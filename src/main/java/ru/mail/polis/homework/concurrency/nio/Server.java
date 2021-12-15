package ru.mail.polis.homework.concurrency.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static ru.mail.polis.homework.concurrency.nio.ObjectSerializer.deserialize;

/**
 * Слушает несколько портов. Принимает список операций, производит с ними вычисления
 * и отправляет результат на указанный порт. Для каждого клиента свой уникальный порт.
 * Чтение из всех сокетов должно происходить в один поток.
 * Отправка результата тоже в один поток, отличный от потока чтения.
 * <p>
 * Вычисления должны производиться в отдельном экзекьюторе.
 * <p>
 * Сервер умеет принимать сигналы о закрытии запроса (должен перестать принимать/вычислять и отправлять что либо
 * по заданному запросу) и о закрытии клиента (должен завершить все вычисления от клиента и закрыть клиентский сокет).
 */
public class Server {

    private static final int BUFFER_SIZE = 1536;
    private static final int META_DATA_REQUEST_CODE = 0;
    private static final int CALCULATING_REQUEST_CODE = 1;
    private static final int CANCELLING_REQUEST_CODE = -1;
    private static final int CLOSE_CLIENT_REQUEST_CODE = -2;

    private final Map<Integer, ServerOperation> resultsMap = new ConcurrentHashMap<>();
    private final Map<Integer, ClientInfo> clientsResultsMap = new ConcurrentHashMap<>();
    private final int[] serverPorts;
    private final ExecutorService calculatingExecutor;
    private final ExecutorService preparingForCalculatingExecutor;
    private final BlockingQueue<ServerAnswer> readyForSendingResultsQueue = new LinkedBlockingQueue<>();
    private SocketChannel answerChannel;

    public Server(int[] serverPorts, int calculateThreadsCount) {
        this.serverPorts = serverPorts;
        preparingForCalculatingExecutor = Executors.newSingleThreadExecutor();
        if (calculateThreadsCount == 1) {
            calculatingExecutor = preparingForCalculatingExecutor;
        } else {
            calculatingExecutor = Executors.newFixedThreadPool(calculateThreadsCount - 1);
        }
    }

    public void start() {
        createListeningClientsThread().start();
        createAnswerThread().start();
    }

    private Thread createListeningClientsThread() {
        return new Thread(() -> {
            try (Selector selector = Selector.open()) {
                Map<SelectionKey, ServerSocketChannel> receivingChannels = new HashMap<>();
                for (int port : serverPorts) {
                    ServerSocketChannel serverSocket = ServerSocketChannel.open();
                    serverSocket.bind(new InetSocketAddress("localhost", port));
                    serverSocket.configureBlocking(false);
                    SelectionKey receivingChannelKey = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
                    receivingChannels.put(receivingChannelKey, serverSocket);
                }
                ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
                while (true) {
                    selector.select();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        if (key.isAcceptable()) {
                            register(selector, receivingChannels.get(key));
                        }
                        if (key.isReadable()) {
                            answer(buffer, key);
                        }
                        iter.remove();
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private Thread createAnswerThread() {
        return new Thread(() -> {
            try {
                while (true) {
                    ServerAnswer serverAnswer = readyForSendingResultsQueue.take();
                    answerChannel = SocketChannel.open(new InetSocketAddress("localhost", resultsMap.get(serverAnswer.id).getAnswerPort()));
                    ByteBuffer answerBuffer = ByteBuffer.allocate(BUFFER_SIZE);
                    answerBuffer.putInt(CALCULATING_REQUEST_CODE);
                    answerBuffer.putInt(serverAnswer.id);
                    answerBuffer.putDouble(serverAnswer.result);
                    answerBuffer.flip();
                    ServerOperation serverOperation = resultsMap.get(serverAnswer.id);
                    synchronized (resultsMap) {
                        if (!serverOperation.getServerState().equals(ServerState.CANCEL) && !serverOperation.getServerState().equals(ServerState.CLOSE)) {
                            serverOperation.setServerState(ServerState.SENDING);
                        }
                    }
                    answerChannel.write(answerBuffer);
                    synchronized (resultsMap) {
                        if (!serverOperation.getServerState().equals(ServerState.CANCEL) && !serverOperation.getServerState().equals(ServerState.CLOSE)) {
                            serverOperation.setServerState(ServerState.DONE);
                        }
                    }
                    answerBuffer.clear();
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
    }

    private void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    private void answer(ByteBuffer buffer, SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        buffer.flip();
        int requestCode = buffer.getInt();
        switch (requestCode) {
            case CLOSE_CLIENT_REQUEST_CODE:
                processCloseRequest(buffer);
                return;
            case CANCELLING_REQUEST_CODE:
                processCancelRequest(buffer);
                return;
            case META_DATA_REQUEST_CODE:
                processMetaData(buffer);
                return;
            case CALCULATING_REQUEST_CODE:
                processCalculateRequest(buffer);
                return;
        }
        throw new UnsupportedOperationException();
    }

    private void processCalculateRequest(ByteBuffer buffer) throws IOException, ClassNotFoundException {
        int resultId = buffer.getInt();
        if (!resultsMap.containsKey(resultId)) {
            resultsMap.put(resultId, new ServerOperation());
        }
        ServerOperation currentServerOperation = resultsMap.get(resultId);
        ServerState operationServerState = currentServerOperation.getServerState();
        if (operationServerState.equals(ServerState.CANCEL) || operationServerState.equals(ServerState.CLOSE) || operationServerState.equals(ServerState.DONE)) {
            return;
        }
        if (operationServerState.equals(ServerState.LOADING)) {
            currentServerOperation.setServerState(ServerState.WAITING_CALCULATE);
        }
        int operationsOrder = buffer.getInt();
        int operationsOrderSubListNumber = buffer.getInt();
        int operandsInBuffer = buffer.getInt();
        List<Operand> operands = new LinkedList<>();
        for (int i = 0; i < operandsInBuffer; i++) {
            byte[] bytes = new byte[buffer.getInt()];
            buffer.get(bytes);
            operands.add((Operand) deserialize(bytes));
        }
        Runnable runnable = () -> {
            if (operationServerState.equals(ServerState.WAITING_CALCULATE)) {
                currentServerOperation.setServerState(ServerState.CALCULATING);
            }
            Map<Integer, TreeMap<Integer, List<Operand>>> operationOrderResults = currentServerOperation.getOperationOrderResults();
            Lock lock;
            synchronized (currentServerOperation) {
                Map<Integer, ReentrantLock> receivingCalculationLocks = currentServerOperation.getReceivingCalculationLocks();
                if (!receivingCalculationLocks.containsKey(operationsOrder)) {
                    receivingCalculationLocks.put(operationsOrder, new ReentrantLock());
                }
                lock = receivingCalculationLocks.get(operationsOrder);
            }
            try {
                lock.lock();
                if (!operationOrderResults.containsKey(operationsOrder)) {
                    TreeMap<Integer, List<Operand>> operationsOrderSubLists = new TreeMap<>();
                    operationsOrderSubLists.put(operationsOrderSubListNumber, operands);
                    operationOrderResults.put(operationsOrder, operationsOrderSubLists);
                } else {
                    operationOrderResults.get(operationsOrder).put(operationsOrderSubListNumber, operands);
                }
                currentServerOperation.addReceivedOperands(operands.size());
            } finally {
                lock.unlock();
            }
            if (currentServerOperation.getTotalOperands() != 0 && currentServerOperation.getTotalOperands() == currentServerOperation.getReceivedOperands()) {
                calculateAllPartsAndQueueForSending(resultId);
            }
        };
        preparingForCalculatingExecutor.execute(runnable);
        buffer.flip();
        buffer.clear();
    }

    private void calculateAllPartsAndQueueForSending(int resultId) {
        calculatingExecutor.execute(() -> {
            ServerOperation currentServerOperation = resultsMap.get(resultId);
            ServerState operationServerState = currentServerOperation.getServerState();
            if (operationServerState.equals(ServerState.WAITING_CALCULATE)) {
                currentServerOperation.setServerState(ServerState.CALCULATING);
            }
            List<Operand> totalCalculation = new LinkedList<>();
            Map<Integer, TreeMap<Integer, List<Operand>>> operationOrderResults = currentServerOperation.getOperationOrderResults();
            for (Integer operationOrder : operationOrderResults.keySet()) {
                TreeMap<Integer, List<Operand>> operationsOrderSubLists = operationOrderResults.get(operationOrder);
                for (Integer operationsOrderSubListNumber : operationsOrderSubLists.keySet()) {
                    totalCalculation.addAll(operationsOrderSubLists.get(operationsOrderSubListNumber));
                }
            }
            double totalResult = OperandsParser.parseAndCalculate(totalCalculation);
            readyForSendingResultsQueue.add(new ServerAnswer(resultId, totalResult));
            currentServerOperation.setServerState(ServerState.WAITING_TO_SEND);
        });
    }

    private void processMetaData(ByteBuffer buffer) {
        int resultId = buffer.getInt();
        if (!resultsMap.containsKey(resultId)) {
            resultsMap.put(resultId, new ServerOperation());
        }
        ServerOperation currentServerOperation = resultsMap.get(resultId);
        if (currentServerOperation.getServerState().equals(ServerState.LOADING)) {
            currentServerOperation.setServerState(ServerState.WAITING_CALCULATE);
        }
        int clientId = buffer.getInt();
        currentServerOperation.setAnswerPort(buffer.getInt());
        currentServerOperation.setTotalOperands(buffer.getInt());
        synchronized (clientsResultsMap) {
            if (!clientsResultsMap.containsKey(clientId)) {
                clientsResultsMap.put(clientId, new ClientInfo());
            }
            ClientInfo clientInfo = clientsResultsMap.get(clientId);
            if (!clientInfo.isClosed()) {
                clientInfo.addResultId(resultId);
            }
        }
        buffer.flip();
        buffer.clear();
        if (currentServerOperation.getTotalOperands() == currentServerOperation.getReceivedOperands()) {
            calculateAllPartsAndQueueForSending(resultId);
        }
    }

    private void processCloseRequest(ByteBuffer buffer) throws IOException {
        int clientId = buffer.getInt();
        if (!clientsResultsMap.containsKey(clientId)) {
            clientsResultsMap.put(clientId, new ClientInfo());
        }
        clientsResultsMap.get(clientId).setClosed();
        for (Integer resultId : clientsResultsMap.get(clientId).resultIds) {
            resultsMap.get(resultId).setServerState(ServerState.CLOSE);
        }
        SocketChannel closingClientAnswerChannel = SocketChannel.open(new InetSocketAddress("localhost", buffer.getInt()));
        buffer.flip();
        buffer.clear();
        buffer.putInt(CLOSE_CLIENT_REQUEST_CODE);
        buffer.flip();
        closingClientAnswerChannel.write(buffer);
        buffer.clear();
    }

    private void processCancelRequest(ByteBuffer buffer) {
        int resultId = buffer.getInt();
        if (!resultsMap.containsKey(resultId)) {
            resultsMap.put(resultId, new ServerOperation());
        }
        ServerOperation currentServerOperation = resultsMap.get(resultId);
        if (!currentServerOperation.getServerState().equals(ServerState.DONE) && !currentServerOperation.getServerState().equals(ServerState.CLOSE)) {
            resultsMap.get(resultId).setServerState(ServerState.CANCEL);
        }
        buffer.flip();
        buffer.clear();
    }


    /**
     * Можно редактировать метод. Должен вернуть всех клиентов
     */
    public Set<Integer> getClients() {
        return clientsResultsMap.keySet();
    }

    /**
     * Можно редактировать метод. Должен вернуть все операции для заданного клиента.
     * Если клиента нет или он уже закрыт -- вернуть null. Если клиент есть, но операций нет -- вернуть пустой список.
     */
    public Map<Integer, ServerOperation> getOperationsForClient(int clientId) {
        synchronized (clientsResultsMap) {
            if (!clientsResultsMap.containsKey(clientId) || clientsResultsMap.get(clientId).isClosed()) {
                return null;
            }
            return resultsMap.entrySet().stream()
                    .filter(resultIdServerOperationEntry -> clientsResultsMap.containsKey(resultIdServerOperationEntry.getKey())
                            && resultIdServerOperationEntry.getValue().getTotalOperands() > 0)
                    .collect(Collectors.toMap(
                            Map.Entry::getKey,
                            Map.Entry::getValue
                            )
                    );
        }
    }

    private static class ServerAnswer {
        private final int id;
        private final double result;

        public ServerAnswer(int id, double result) {
            this.id = id;
            this.result = result;
        }
    }

    private static class ClientInfo {
        private final Set<Integer> resultIds = new HashSet<>();
        private boolean closed = false;

        public void addResultId(int id) {
            resultIds.add(id);
        }

        public void setClosed() {
            closed = true;
        }

        public boolean isClosed() {
            return closed;
        }

    }
}
