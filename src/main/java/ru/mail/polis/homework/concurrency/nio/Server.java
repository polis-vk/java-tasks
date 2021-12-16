package ru.mail.polis.homework.concurrency.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import static java.nio.channels.SelectionKey.OP_READ;

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
    private static final String HOST_NAME = "localhost";
    private static final byte OPERATION_HEADER = -1;
    private static final byte CLOSE_SERVER_REQUEST = -2;
    private static final byte CL0SE_CLIENT_REQUEST = -3;

    private final Map<Integer, List<ServerOperation>> activeClients = new HashMap<>();
    private final BlockingQueue<ServerOperation> resultsToSend = new LinkedBlockingQueue<>();
    private final ByteBuffer readBuffer = ByteBuffer.allocateDirect(1024);
    private final Selector selector;
    private final ExecutorService executorService;
    private boolean inProcess = true;

    public Server(int[] serverPorts, int calculateThreadsCount) throws IOException {
        executorService = Executors.newFixedThreadPool(calculateThreadsCount);
        new ResultCarrier().start();
        try (Selector selector = Selector.open()) {
            for (int port : serverPorts) {
                try (ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()) {
                    serverSocketChannel.bind(new InetSocketAddress(HOST_NAME, port));
                    serverSocketChannel.configureBlocking(false);
                    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                }
            }
            this.selector = selector;
        }
    }

    /**
     * Можно редактировать метод. Должен вернуть всех клиентов
     */
    public Set<Integer> getClients() {
        return activeClients.keySet();
    }

    /**
     * Можно редактировать метод. Должен вернуть все операции для заданного клиента.
     * Если клиента нет или он уже закрыт -- вернуть null. Если клиент есть, но операций нет -- вернуть пустой список.
     */
    public List<ServerOperation> getOperationsForClient(int clientID) {
        return activeClients.get(clientID);
    }

    private void run() throws IOException {
        while (inProcess) {
            selector.select();
            Iterator<SelectionKey> selectedKeys = selector.selectedKeys().iterator();
            while (selectedKeys.hasNext()) {
                SelectionKey key = selectedKeys.next();
                if (key.isAcceptable()) {
                    accept(key);
                }
                if (key.isReadable()) {
                    read(key);
                }
                selectedKeys.remove();
            }
        }
    }

    private void shutDown() {
        inProcess = false;
        executorService.shutdownNow();
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, OP_READ);
    }

    private void read(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        readBuffer.clear();
        int numRead = socketChannel.read(readBuffer);
        byte[] data = new byte[numRead];
        System.arraycopy(readBuffer.array(), 0, data, 0, numRead);

        byte[] bytes = new byte[4];
        System.arraycopy(data, 1, bytes, 0, 4);
        int clientID = readInt(bytes);
        List<ServerOperation> operations = activeClients.get(clientID);
        ServerOperation operation = operations.get(operations.size() - 1);

        switch (data[0]) {
            case CL0SE_CLIENT_REQUEST:
                activeClients.remove(clientID);
                return;
            case CLOSE_SERVER_REQUEST:
                shutDown();
                return;
            case OPERATION_HEADER:
                processHeader(data, clientID, socketChannel);
        }
        operation.addOperand(data);
        if (operation.isCompleted()) {
            operation.setServerState(ServerState.WAITING_CALCULATE);
            executorService.submit(new Worker(operation));
        }

    }

    private void processHeader(byte[] data, int clientID, SocketChannel socketChannel) {
        byte[] bytes = new byte[4];
        System.arraycopy(data, 5, bytes, 0, 4);
        int numberOfOperands = readInt(bytes);
        System.arraycopy(data, 9, bytes, 0, 4);
        int resultPort = readInt(bytes);
        ServerOperation serverOperation = new ServerOperation(socketChannel, clientID, numberOfOperands, resultPort);
        serverOperation.setServerState(ServerState.LOADING);
        activeClients.computeIfAbsent(clientID, k -> new ArrayList<>()).add(serverOperation);
    }

    private int readInt(byte[] bytes) {
        return ((bytes[3] << 24) + (bytes[2] << 16) + (bytes[1] << 8) + (bytes[0]));
    }

    private byte[] writeInt(int value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) (value)
        };
    }

    private class Worker implements Runnable {
        private final ServerOperation serverOperation;

        public Worker(ServerOperation serverOperation) {
            this.serverOperation = serverOperation;
        }

        private int calculate(List<Operand> operands) {
            return 0;
        }

        @Override
        public void run() {
            serverOperation.setServerState(ServerState.CALCULATING);
            int result = calculate(serverOperation.getOperands());
            serverOperation.setResult(result);
            serverOperation.setServerState(ServerState.WAITING_TO_SEND);
            resultsToSend.add(serverOperation);
        }
    }

    private class ResultCarrier extends Thread {

        @Override
        public void run() {
            try {
                while (inProcess) {
                    ServerOperation result = resultsToSend.take();
                    send(result);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        private void send(ServerOperation serverOperation) {
            serverOperation.setServerState(ServerState.SENDING);
            try (SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress(HOST_NAME, serverOperation.getResultPort()))) {
                socketChannel.write(ByteBuffer.wrap(writeInt(serverOperation.getResult())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            serverOperation.setServerState(ServerState.DONE);
        }
    }

}
