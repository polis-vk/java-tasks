package ru.mail.polis.homework.concurrency.nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;
import java.util.concurrent.ExecutorService;
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

    private final ExecutorService threadPool;
    private final int serverPort;
    private SocketChannel[] clientSockets;
    private final AtomicInteger freeWorkers;
    private int resultId = 0;
    private final Map<Integer, Result> resultMap = new HashMap<>();

    /**
     * @param clientsPort         массив портов для отправки
     * @param serverPort          порт для принятия
     * @param threadsCountForSend максимальное кол-во потоков, которое можно использовать.
     *                            Дано, что threadsCountForSend >= clientsPort.length
     */
    public Client(int[] clientsPort, int serverPort, int threadsCountForSend) {
        threadPool = Executors.newFixedThreadPool(threadsCountForSend);
        this.serverPort = serverPort;
        openClientSockets(clientsPort);
        threadPool.submit(new ListeningRunnable());
        freeWorkers = new AtomicInteger(threadsCountForSend - 1);
    }

    /**
     * Отправляет на сервер операции в несколько потоков. Плюс отправляет порт, на котором будет слушать ответ.
     * Важно, не потеряйте порядок операндов
     * Возвращает Result с отложенным заполнением ответа.
     */
    public Result calculate(List<Operand> operands) {
        int partitionSize = operands.size() / freeWorkers.get();
        List<List<Operand>> operandPartitions = partitionOperandsList(partitionSize, operands);
        for (int i = 0; i < operandPartitions.size(); i++) {
            OperandWrapper operandWrapper = new OperandWrapper(resultId, i, operandPartitions.size(), operandPartitions.get(i), serverPort);
            threadPool.submit(new SendingRunnable(operandWrapper, getRandomNumber(clientSockets.length - 1)));
        }
        resultMap.put(resultId, new Result(resultId));
        return resultMap.get(resultId++);
    }

    /**
     * Возвращает результат, для заданной айдишки запроса
     */
    public Result getResult(int id) {
        if (!resultMap.containsKey(id)) {
            return null;
        }
        return resultMap.get(id);
    }

    /**
     * Закрывает клиента и всего его запросы.
     */
    public void close() {
        threadPool.shutdown();
    }

    private void openClientSockets(int[] clientsPort) {
        clientSockets = new SocketChannel[clientsPort.length];
        try {
            for (int i = 0; i < clientsPort.length; i++) {
                clientSockets[i] = SocketChannel.open(new InetSocketAddress("localhost", clientsPort[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ListeningRunnable implements Runnable {
        @Override
        public void run() {
            try (Selector selector = Selector.open()) {
                try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
                    serverSocket.bind(new InetSocketAddress("localhost", serverPort));
                    serverSocket.configureBlocking(false);
                    serverSocket.register(selector, SelectionKey.OP_ACCEPT);
                    ByteBuffer buffer = ByteBuffer.allocate(256);

                    while (true) {
                        selector.select();
                        Set<SelectionKey> selectedKeys = selector.selectedKeys();
                        Iterator<SelectionKey> iter = selectedKeys.iterator();
                        while (iter.hasNext()) {

                            SelectionKey key = iter.next();

                            if (key.isAcceptable()) {
                                register(selector, serverSocket);
                            }

                            if (key.isReadable()) {
                                onResultReceive(buffer, key);
                            }
                            iter.remove();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void onResultReceive(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        ServerResult serverResult = (ServerResult) convertBytesToObject(buffer.array());
        int id = serverResult.getId();
        double result = serverResult.getResult();
        resultMap.get(id).setResult(result);
        buffer.clear();
    }

    private static void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    private class SendingRunnable implements Runnable {

        private final OperandWrapper operandWrapper;
        private final int clientSocketIndex;

        public SendingRunnable(OperandWrapper operandWrapper, int clientSocketIndex) {
            this.operandWrapper = operandWrapper;
            this.clientSocketIndex = clientSocketIndex;
        }

        @Override
        public void run() {
            freeWorkers.decrementAndGet();
            ByteBuffer byteBuffer = ByteBuffer.wrap(convertObjectToBytes(operandWrapper));
            try {
                clientSockets[clientSocketIndex].write(byteBuffer);
                byteBuffer.clear();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                freeWorkers.incrementAndGet();
            }
        }
    }

    public static byte[] convertObjectToBytes(Object obj) {
        ByteArrayOutputStream boas = new ByteArrayOutputStream();
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(boas)) {
            objectOutputStream.writeObject(obj);
            return boas.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    public static Object convertBytesToObject(byte[] data) {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        try (ObjectInputStream is = new ObjectInputStream(in)) {
            return is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    private int getRandomNumber(int max) {
        return (int) ((Math.random() * (max)) + 0);
    }

    private List<List<Operand>> partitionOperandsList(int partitionSize, List<Operand> operands) {
        List<List<Operand>> result = new ArrayList<>();
        int counter = 0;

        for (Operand operand : operands) {
            if (counter % partitionSize == 0) {
                result.add(new ArrayList<>());
            }
            counter++;
            result.get(result.size() - 1).add(operand);
        }
        return result;
    }
}
