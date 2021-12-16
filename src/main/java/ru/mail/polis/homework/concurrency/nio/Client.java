package ru.mail.polis.homework.concurrency.nio;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Клиент, который принимает список операций и как можно быстрее отправляет это на сервер. (ClientSocket)
 * Нужно отправлять в несколько потоков. Важный момент, одно задание на 1 порт.
 * <p>
 * Так же, он передает информацию серверу, на каком порте он слушает результат (ServerSocket).
 * При этом слушатель серверСокета только 1
 * <p>
 * Каждому запросу присваивается уникальная интовая айдишка. Клиент может быть закрыт.
 */
public class Client {
    private static final int RESULT_RESPONSE = 0;
    private static final int OPERATION_RECEIVED_RESPONSE = 1;
    private static final int REQUEST_CANCELED_RESPONSE = 2;

    // all messages start with clientId and message type code
    private static final int OPERATION_HEADER_MESSAGE = 0;
    private static final int OPERAND_MESSAGE = 1;
    private static final int CLOSE_CLIENT_MESSAGE = 2;
    private static final int CANSEL_REQUEST_MESSAGE = 3;
    private static final int REGISTER_CLIENT_MESSAGE = 4;

    private final List<SocketChannel> clientSockets = new ArrayList<>();
    private final Executor sendExecutor;
    private final Map<Integer, Result> requests = new HashMap<>();
    private final Integer serverPort;
    private final AtomicInteger nextOperationId = new AtomicInteger(0);
    private Integer nextSocketToUse = 0;
    private final Integer clientId;
    private Thread answerProcessingThread;
    private ServerSocketChannel serverSocket;

    /**
     * @param clientsPort         массив портов для отправки
     * @param serverPort          порт для принятия
     * @param threadsCountForSend максимальное кол-во потоков, которое можно использовать.
     * @param clientId            уникальный id клиента
     *                            Дано, что threadsCountForSend >= clientsPort.length
     */
    public Client(int[] clientsPort, int serverPort, int threadsCountForSend, int clientId) {
        this.clientId = clientId;
        sendExecutor = Executors.newFixedThreadPool(threadsCountForSend);
        answerProcessingThread = new Thread(() -> {
            try (Selector selector = Selector.open()) {
                serverSocket = ServerSocketChannel.open();
                serverSocket.bind(new InetSocketAddress("localhost", serverPort));
                serverSocket.configureBlocking(false);
                serverSocket.register(selector, SelectionKey.OP_ACCEPT);
                ByteBuffer buffer = ByteBuffer.allocate(256);
                while (true) {
                    selector.select();
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iterator = selectedKeys.iterator();
                    while (iterator.hasNext()) {

                        SelectionKey key = iterator.next();

                        if (key.isAcceptable()) {
                            register(selector, serverSocket);
                        }

                        if (key.isReadable()) {
                            processAnswer(buffer, key);
                        }
                        iterator.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        answerProcessingThread.start();
        this.serverPort = serverPort;

        for (int clientPort : clientsPort) {
            try {
                SocketChannel client = SocketChannel.open(new InetSocketAddress("localhost", clientPort));
                synchronized (clientSockets) {
                    clientSockets.add(client);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // actually we should wait until server answers that is registers the user, but it will add a lot of
        // complexity, so we assume that server will register user before it will receive requests,
        // produced by calculate
        SocketChannel chanel = peekClientChanel();
        try {
            registerOnServerRequest(chanel, serverPort);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Отправляет на сервер операции в несколько потоков. Плюс отправляет порт, на котором будет слушать ответ.
     * Важно, не потеряйте порядок операндов
     * Возвращает Result с отложенным заполнением ответа.
     */
    public Result calculate(List<Operand> operands) {
        SocketChannel chanel = peekClientChanel();
        Integer operationId = nextOperationId.incrementAndGet();
        try {
            writeOperationHeader(chanel, operationId, operands.size());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Result newResult = new Result(operationId);
        newResult.setState(ClientState.START);
        synchronized (requests) {
            requests.put(operationId, newResult);
        }

        int operandOrder = 0;
        for (Operand operand : operands) {
            chanel = peekClientChanel();
            SocketChannel finalClient = chanel;
            int finalOperandOrder = operandOrder;
            sendExecutor.execute(() -> {
                newResult.setState(ClientState.SENDING);
                try {
                    writeOperand(finalClient, operationId, finalOperandOrder, operand);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            ++operandOrder;
        }

        return newResult;
    }

    public void cancelOperation(int id) {
        if (getResult(id) != null) {
            Result result = getResult(id);
            synchronized (result) {
                if (result.getState() == ClientState.START) {
                    result.setState(ClientState.CANCEL);
                } else if (result.getState() != ClientState.CANCEL && result.getState() != ClientState.DONE
                        && result.getState() != ClientState.CLOSE && result.getState() != ClientState.CANCELLING) {
                    result.setState(ClientState.CANCELLING);
                    SocketChannel client = peekClientChanel();
                    try {
                        canselOperationRequest(client, id);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Возвращает результат, для заданной айдишки запроса
     */
    public Result getResult(int id) {
        synchronized (requests) {
            return requests.getOrDefault(id, null);
        }
    }

    /**
     * Закрывает клиента и всего его запросы.
     */
    public void close() {
        SocketChannel client = peekClientChanel();
        synchronized (requests) {
            for (Result request : requests.values()) {
                request.setState(ClientState.CLOSE);
            }
        }
        synchronized (client) {
            try {
                writeCloseCommand(client);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        answerProcessingThread.interrupt();
    }

    // we use Round Robin algorithm to peek socket
    private SocketChannel peekClientChanel() {
        Integer socketNumber;
        synchronized (nextSocketToUse) {
            socketNumber = nextSocketToUse;
            nextSocketToUse = (nextSocketToUse + 1) % clientSockets.size();
        }
        SocketChannel result;
        synchronized (clientSockets) {
            result = clientSockets.get(socketNumber);
        }
        return result;
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {
        SocketChannel chanel = serverSocket.accept();
        chanel.configureBlocking(false);
        chanel.register(selector, SelectionKey.OP_READ);
    }


    private void writeOperationHeader(SocketChannel chanel, Integer operationId,
                                      Integer operandsAmount) throws IOException {
        synchronized (chanel) {
            ByteBuffer bb = ByteBuffer.allocate(256);
            bb.putInt(clientId);
            bb.putInt(OPERATION_HEADER_MESSAGE);
            bb.putInt(operationId);
            bb.putInt(operandsAmount);
            bb.rewind();
            chanel.write(bb);
        }
    }

    private void canselOperationRequest(SocketChannel chanel, Integer operationId) throws IOException {
        synchronized (chanel) {
            System.out.println("Client: send cansel");
            ByteBuffer bb = ByteBuffer.allocate(256);
            bb.putInt(clientId);
            bb.putInt(CANSEL_REQUEST_MESSAGE);
            bb.putInt(operationId);
            bb.rewind();
            chanel.write(bb);
        }
    }

    private void registerOnServerRequest(SocketChannel chanel, Integer responsePort)
            throws IOException {
        synchronized (chanel) {
            System.out.println("Client: send register");
            ByteBuffer bb = ByteBuffer.allocate(256);
            bb.putInt(clientId);
            bb.putInt(REGISTER_CLIENT_MESSAGE);
            bb.putInt(responsePort);
            bb.rewind();
            chanel.write(bb);
        }
    }

    private void writeOperand(SocketChannel chanel, Integer operationId, Integer operandOrder,
                              Operand operand) throws IOException {
        synchronized (chanel) {
            ByteBuffer bb = ByteBuffer.allocate(256);
            System.out.println("Client: send operand");
            bb.putInt(clientId);
            bb.putInt(OPERAND_MESSAGE);
            bb.putInt(operationId);
            bb.putInt(operandOrder);
            operand.writeIntoBuffer(bb);
            bb.rewind();
//            bb.put(operand.getCharacterRepresentation());
            chanel.write(bb);
        }
    }

    private void writeCloseCommand(SocketChannel chanel) throws IOException {
        synchronized (chanel) {
            System.out.println("Client: send close");
            ByteBuffer bb = ByteBuffer.allocate(256);
            bb.putInt(clientId);
            bb.putInt(CLOSE_CLIENT_MESSAGE);
            bb.rewind();
            chanel.write(bb);
        }
    }

    private void processAnswer(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();
        synchronized (channel) {
            channel.read(buffer);
        }

        buffer.rewind();

        Integer answerType = buffer.getInt();
        Integer resultId = buffer.getInt();

        if (answerType == RESULT_RESPONSE) {
            System.out.println("Client: got result");
            Double resultValue = buffer.getDouble();

            if (getResult(resultId) != null) {
                Result result = getResult(resultId);
                result.setState(ClientState.DONE);
                result.setValue(resultValue);
                System.out.println("Client: got result: " + resultValue);
                synchronized (result) {
                    result.notifyListeners();
                }
            }
        } else if (answerType == OPERATION_RECEIVED_RESPONSE) {
            System.out.println("Client: got received");
            if (getResult(resultId) != null) {
                Result result = getResult(resultId);
                result.setState(ClientState.SENT);
            }
        } else if (answerType == REQUEST_CANCELED_RESPONSE) {
            System.out.println("Client: got cansel");
            if (getResult(resultId) != null) {
                Result result = getResult(resultId);
                result.setState(ClientState.CANCEL);
            }
        } else {
            throw new RemoteException();
        }

        buffer.clear();
    }

}
