package ru.mail.polis.homework.concurrency.nio;

import com.sun.media.sound.InvalidDataException;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.mail.polis.homework.concurrency.nio.ObjectSerializer.serialize;

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

    //private static ByteBuffer buffer;
    private static final int BUFFER_SIZE = 1536;
    private static final int META_DATA_REQUEST_CODE = 0;
    private static final int CALCULATING_REQUEST_CODE = 1;
    private static final int CANCELLING_REQUEST_CODE = -1;
    private static final int CLOSE_CLIENT_REQUEST_CODE = -2;

    private static final AtomicInteger resultIdCounter = new AtomicInteger(1);
    private static final AtomicInteger clientIdCounter = new AtomicInteger(1);
    private final int clientId = clientIdCounter.getAndIncrement();
    private final CountDownLatch closeClientCountDown = new CountDownLatch(1);
    private final Map<Integer, Result> resultMap = new ConcurrentHashMap<>();
    private final List<SocketChannel> sendingChannels = new CopyOnWriteArrayList<>();
    private int[] clientsPort;
    private int serverPort;
    private int threadsCountForSend;
    private ExecutorService executor;
    private volatile boolean isClosed = false;

    /**
     * @param clientsPort         массив портов для отправки
     * @param serverPort          порт для принятия
     * @param threadsCountForSend максимальное кол-во потоков, которое можно использовать.
     *                            Дано, что threadsCountForSend >= clientsPort.length
     */
    public Client(int[] clientsPort, int serverPort, int threadsCountForSend) {
        try {
            this.clientsPort = clientsPort;
            this.serverPort = serverPort;
            this.threadsCountForSend = threadsCountForSend;
            for (int port : clientsPort) {
                sendingChannels.add(SocketChannel.open(new InetSocketAddress("localhost", port)));
            }
            executor = Executors.newFixedThreadPool(threadsCountForSend);
            Thread resultGettingThread = createResultGettingThread(serverPort);
            resultGettingThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Thread createResultGettingThread(int serverPort) {
        return new Thread(() -> {
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            try (Selector selector = Selector.open()) {
                ServerSocketChannel receivingChannel = ServerSocketChannel.open();
                receivingChannel.bind(new InetSocketAddress("localhost", serverPort));
                receivingChannel.configureBlocking(false);
                receivingChannel.register(selector, SelectionKey.OP_ACCEPT);
                while (!isClosed) {
                    selector.select();
                    Set<SelectionKey> selectionKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectionKeys.iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        if (key.isAcceptable()) {
                            register(selector, receivingChannel);
                        }
                        if (key.isReadable()) {
                            getAnswer(buffer, key);
                        }
                        iter.remove();
                    }
                }
                receivingChannel.close();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
    }

    private void register(Selector selector, ServerSocketChannel serverSocket) throws IOException {
        SocketChannel client = serverSocket.accept();
        client.configureBlocking(false);
        client.register(selector, SelectionKey.OP_READ);
    }

    private void getAnswer(ByteBuffer buffer, SelectionKey key) throws IOException, ClassNotFoundException {
        SocketChannel receivingClient = (SocketChannel) key.channel();
        receivingClient.read(buffer);
        buffer.flip();
        int responseCode = buffer.getInt();
        if (responseCode == CLOSE_CLIENT_REQUEST_CODE) {
            isClosed = true;
            buffer.flip();
            buffer.clear();
            closeClientCountDown.countDown();
            return;
        }
        int resultId = buffer.getInt();
        if (resultId < 1) {
            throw new InvalidDataException();
        }
        Result result = resultMap.get(resultId);
        result.setState(ClientState.RECEIVING);
        double resultValue = buffer.getDouble();
        result.set(resultValue);
        result.setState(ClientState.DONE);
        synchronized (result) {
            if (result.isWaiting()) {
                result.notify();
            }
        }
        buffer.flip();
        buffer.clear();
    }

    /**
     * Отправляет на сервер операции в несколько потоков. Плюс отправляет порт, на котором будет слушать ответ.
     * Важно, не потеряйте порядок операндов
     * Возвращает Result с отложенным заполнением ответа.
     */
    public Result calculate(List<Operand> operands) throws IOException {
        int maxThreadsCanUsed = Math.min(operands.size(), threadsCountForSend);
        int resultId = resultIdCounter.getAndIncrement();
        Result calculatingResult = new Result(resultId);
        calculatingResult.setClient(this);
        calculatingResult.setState(ClientState.START);
        resultMap.put(resultId, calculatingResult);
        int oneChannelOperandsNumber = operands.size() / maxThreadsCanUsed;
        int leastChannelOperandsNumber = oneChannelOperandsNumber + operands.size() % maxThreadsCanUsed;
        Runnable runnable1 = () -> {
            List<Operand> list = operands.subList(0, leastChannelOperandsNumber);
            try {
                resultMap.get(resultId).setState(ClientState.SENDING);
                sendMetaDataWithChannel(sendingChannels.get(0), resultId, serverPort, operands.size());
                sendWithChannel(sendingChannels.get(0), resultId, list, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
        };
        executor.execute(runnable1);
        for (int i = 1; i < maxThreadsCanUsed; i++) {
            int finalI = i;
            Runnable runnable = () -> {
                int k = leastChannelOperandsNumber + (finalI - 1) * oneChannelOperandsNumber;
                List<Operand> list = operands.subList(k, k + oneChannelOperandsNumber);
                try {
                    sendWithChannel(sendingChannels.get(finalI % clientsPort.length), resultId, list, finalI + 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            };
            executor.execute(runnable);
        }
        resultMap.get(resultId).setState(ClientState.SENT);
        return calculatingResult;
    }

    public void cancelResult(int id) throws IOException {
        if (!resultMap.containsKey(id)) {
            throw new RuntimeException("There is no result with id = " + id);
        }
        Result result = resultMap.get(id);
        if (!result.getState().equals(ClientState.DONE)) {
            result.setState(ClientState.CANCELLING);
            int sendingChannelIndex = (int) (Math.random() * sendingChannels.size());
            ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
            buffer.putInt(id);
            buffer.putInt(CANCELLING_REQUEST_CODE);
            buffer.rewind();
            sendingChannels.get(sendingChannelIndex).write(buffer);
            buffer.clear();
        }
        if (!result.getState().equals(ClientState.DONE)) {
            result.setState(ClientState.CANCEL);
        } else {
            result.setState(ClientState.DONE);
            synchronized (result) {
                if (result.isWaiting()) {
                    result.notify();
                }
            }
        }
    }

    private void sendCloseRequestWithChannel(SocketChannel sendingChannel) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.putInt(CLOSE_CLIENT_REQUEST_CODE);
        buffer.putInt(clientId);
        buffer.putInt(serverPort);
        buffer.rewind();
        sendingChannel.write(buffer);
        buffer.clear();
    }

    private void sendMetaDataWithChannel(SocketChannel sendingChannel, int resultId, int serverPort, int totalOperands) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.putInt(META_DATA_REQUEST_CODE);
        buffer.putInt(resultId);
        buffer.putInt(clientId);
        buffer.putInt(serverPort);
        buffer.putInt(totalOperands);
        buffer.rewind();
        sendingChannel.write(buffer);
        buffer.clear();
    }

    private void sendWithChannel(SocketChannel sendingChannel, int resultId, List<Operand> list, int operationsOrder) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        buffer.putInt(CALCULATING_REQUEST_CODE);
        buffer.putInt(resultId);
        buffer.putInt(operationsOrder);
        int operationsSubListNumber = 1;
        buffer.putInt(operationsSubListNumber);
        buffer.mark();
        int operandsInBuffer = 0;
        buffer.putInt(operandsInBuffer);
        for (Operand operand : list) {
            byte[] operandBytes = serialize(operand);
            if (buffer.capacity() > buffer.position() + Integer.BYTES + operandBytes.length) {
                buffer.putInt(operandBytes.length);
                buffer.put(operandBytes);
                operandsInBuffer++;
            } else {
                buffer.reset();
                buffer.putInt(operandsInBuffer);
                buffer.rewind();
                sendingChannel.write(buffer);
                buffer.clear();
                buffer.putInt(CALCULATING_REQUEST_CODE);
                buffer.putInt(resultId);
                buffer.putInt(operationsOrder);
                buffer.putInt(++operationsSubListNumber);
                buffer.mark();
                operandsInBuffer = 1;
                buffer.putInt(operandsInBuffer);
                buffer.putInt(operandBytes.length);
                buffer.put(operandBytes);
            }
        }
        buffer.reset();
        buffer.putInt(operandsInBuffer);
        buffer.rewind();
        sendingChannel.write(buffer);
        buffer.clear();
    }

    /**
     * Возвращает результат, для заданной айдишки запроса
     */
    public Result getResult(int id) {
        if (!resultMap.containsKey(id)) {
            throw new RuntimeException("There is no result with id = " + id);
        }
        return resultMap.get(id);
    }

    /**
     * Закрывает клиента и всего его запросы.
     */
    public void close() throws IOException {
        try {
            synchronized (this) {
                if (!isClosed) {
                    executor.shutdown();
                    sendCloseRequestWithChannel(sendingChannels.get(0));
                    closeClientCountDown.await();
                    resultMap.values().forEach(result -> result.setState(ClientState.CLOSE));
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getClientId() {
        return clientId;
    }
}
