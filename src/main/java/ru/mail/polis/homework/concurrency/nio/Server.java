package ru.mail.polis.homework.concurrency.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private final Map<Integer, ServerOperation> serverOperations = new HashMap<>();
    private final ExecutorService threadPool;
    //private SocketChannel[] clientSockets;
    private final int[] serverPorts;

    public Server(int[] serverPorts, int calculateThreadsCount) {
        threadPool = Executors.newFixedThreadPool(calculateThreadsCount);
        this.serverPorts = serverPorts;
        threadPool.submit(new ListeningRunnable());
    }

    /**
     * Можно редактировать метод. Должен вернуть всех клиентов
     */
    public void getClients() {

    }

    /**
     * Можно редактировать метод. Должен вернуть все операции для заданного клиента.
     * Если клиента нет или он уже закрыт -- вернуть null. Если клиент есть, но операций нет -- вернуть пустой список.
     */
    public List<ServerOperation> getOperationsForClient() {

        return null;
    }

    private class ListeningRunnable implements Runnable {
        @Override
        public void run() {
            try (Selector selector = Selector.open()) {
                for (int port : serverPorts) {
                    try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
                        serverSocket.bind(new InetSocketAddress("localhost", port));
                        serverSocket.configureBlocking(false);
                        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
                    }
                }
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                while (true) {
                    selector.select();
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectedKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        if (key.isAcceptable()) {
                            register(selector, (ServerSocketChannel) key.channel());
                        }

                        if (key.isReadable()) {
                            onReceive(byteBuffer, key);
                        }
                        iter.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class SendingRunnable implements Runnable {

        private final ServerResult serverResult;
        private final int clientPort;

        public SendingRunnable(ServerResult serverResult, int clientPort) {
            this.serverResult = serverResult;
            this.clientPort = clientPort;
        }

        @Override
        public void run() {
            try {
                SocketChannel client = SocketChannel.open(new InetSocketAddress("localhost", clientPort));
                ByteBuffer byteBuffer = ByteBuffer.wrap(Client.convertObjectToBytes(serverResult));
                client.write(byteBuffer);
                byteBuffer.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class CalculatingRunnable implements Runnable {

        private final OperandWrapper operandWrapper;

        public CalculatingRunnable(OperandWrapper operandWrapper) {
            this.operandWrapper = operandWrapper;
        }

        @Override
        public void run() {
            Operand chunkResult = calculate(operandWrapper.getOperandChunk());
            List<Operand> calculatingResults;
            ServerOperation serverOperation = serverOperations.get(operandWrapper.getServerPort());
            synchronized (serverOperation.getCalculatingResults().get(operandWrapper.getId())) {
                calculatingResults = serverOperation.getCalculatingResults().get(operandWrapper.getId());
                calculatingResults.add(operandWrapper.getOrder(), chunkResult);
                if (calculatingResults.size() == operandWrapper.getSize()) {
                    double result = calculate(calculatingResults).getA();
                    calculatingResults.clear();
                    serverOperation.getResults().get(operandWrapper.getId()).setResult(result);
                    threadPool.submit(new SendingRunnable(serverOperation.getResults().get(operandWrapper.getId()), operandWrapper.getServerPort()));
                }
            }
        }

        private Operand calculate(List<Operand> operands) {
            Operand result = new Operand(0, OperandType.EQUALS);
            for (Operand operand : operands) {
                result = secondOperandCalculate(result, operand);
            }
            return result;
        }

        private Operand secondOperandCalculate(Operand previous, Operand current) {
            if (current.getOperationFirst() != null) {
                switch (current.getOperationFirst()) {
                    case LN: {
                        current = new Operand(Math.log(current.getA()), current.getOperationSecond());
                        break;
                    }
                    case ABS: {
                        current = new Operand(Math.abs(current.getA()), current.getOperationSecond());
                        break;
                    }
                    case SIN: {
                        current = new Operand(Math.sin(current.getA()), current.getOperationSecond());
                        break;
                    }
                    case COS: {
                        current = new Operand(Math.cos(current.getA()), current.getOperationSecond());
                        break;
                    }
                    case TAN: {
                        current = new Operand(Math.tan(current.getA()), current.getOperationSecond());
                        break;
                    }
                    case EXP: {
                        current = new Operand(Math.exp(current.getA()), current.getOperationSecond());
                        break;
                    }
                    case SQUARE: {
                        current = new Operand(Math.sqrt(current.getA()), current.getOperationSecond());
                        break;
                    }
                }
            }
            switch (previous.getOperationSecond()) {
                case PLUS: {
                    return new Operand(previous.getA() + current.getA(), current.getOperationSecond());
                }
                case MINUS: {
                    return new Operand(previous.getA() - current.getA(), current.getOperationSecond());
                }
                case MULT: {
                    return new Operand(previous.getA() * current.getA(), current.getOperationSecond());
                }
                case DIVIDE: {
                    return new Operand(previous.getA() / current.getA(), current.getOperationSecond());
                }
                default: {
                    return current;
                }
            }
        }
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    private void onReceive(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        OperandWrapper operandWrapper = (OperandWrapper) Client.convertBytesToObject(buffer.array());
        putServerOperation(operandWrapper);
        threadPool.submit(new CalculatingRunnable(operandWrapper));
        buffer.clear();
    }

    private void putServerOperation(OperandWrapper operandWrapper) {
        if (!serverOperations.containsKey(operandWrapper.getServerPort())) {
            serverOperations.put(operandWrapper.getServerPort(), new ServerOperation(operandWrapper.getServerPort()));
        } else if (!serverOperations.get(operandWrapper.getServerPort()).getResults().containsKey(operandWrapper.getId())) {
            serverOperations.get(operandWrapper.getServerPort()).getResults().put(operandWrapper.getId(), new ServerResult(operandWrapper.getId()));
            serverOperations.get(operandWrapper.getServerPort()).getCalculatingResults().put(operandWrapper.getId(), new ArrayList<>(operandWrapper.getSize()));
        }
    }
}
