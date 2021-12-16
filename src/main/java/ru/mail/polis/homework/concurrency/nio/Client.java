package ru.mail.polis.homework.concurrency.nio;

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
import java.util.concurrent.ConcurrentHashMap;
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
    private static final int BUFFER_SIZE = 256;
    private static final String HOSTNAME = "localhost";

    private final int serverPort;
    private final SocketChannel[] socketChannels;
    private final ExecutorService executorService;
    private final Map<Integer, Result> results = new ConcurrentHashMap<>();
    private final AtomicInteger resultIdCounter = new AtomicInteger();
    private volatile boolean isClosed;

    /**
     * @param clientsPort         массив портов для отправки
     * @param serverPort          порт для принятия
     * @param threadsCountForSend максимальное кол-во потоков, которое можно использовать.
     *                            Дано, что threadsCountForSend >= clientsPort.length
     */
    public Client(int[] clientsPort, int serverPort, int threadsCountForSend) throws IOException {
        this.serverPort = serverPort;

        socketChannels = new SocketChannel[clientsPort.length];
        try {
            for (int i = 0; i < clientsPort.length; i++) {
                socketChannels[i] = SocketChannel.open(new InetSocketAddress(HOSTNAME, clientsPort[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        executorService = Executors.newFixedThreadPool(threadsCountForSend);
        Thread listener = createListener(serverPort);
        listener.start();
        executorService.submit(listener);
    }

    /**
     * Отправляет на сервер операции в несколько потоков. Плюс отправляет порт, на котором будет слушать ответ.
     * Важно, не потеряйте порядок операндов
     * Возвращает Result с отложенным заполнением ответа.
     */
    public Result calculate(List<Operand> operands) {
        int resultId = resultIdCounter.getAndIncrement();
        Result result = new Result(resultId);
        results.put(resultId, result);

        // TODO: Дописать распределение в executorService и отправку

        results.get(resultId).setState(ClientState.SENT);
        return result;
    }

    /**
     * Возвращает результат, для заданной айдишки запроса
     */
    public Result getResult(int id) {
        return results.get(id);
    }

    /**
     * Закрывает клиента и всего его запросы.
     */
    public void close() {
        isClosed = true;
    }

    /**
     * Создает поток, который будет ждать(слушать) результат от сервера
     *
     * @param serverPort - порт, на котором надо слушать
     * @return поток, который будет слушать результат, пришедший с сервера
     */
    private Thread createListener(int serverPort) {
        return new Thread(() -> {
            try (Selector selector = Selector.open()) {
                try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
                    serverSocket.bind(new InetSocketAddress(HOSTNAME, serverPort));
                    serverSocket.configureBlocking(false);
                    serverSocket.register(selector, SelectionKey.OP_ACCEPT);
                }

                while (!isClosed) {
                    selector.select();
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        iterator.remove();

                        if (key.isAcceptable()) {
                            accept(key);
                        }
                        if (key.isReadable()) {
                            handleRead(key);
                        }
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        socketChannel.register(key.selector(), SelectionKey.OP_READ);
    }

    private void handleRead(SelectionKey key) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
        SocketChannel socketChannel = (SocketChannel) key.channel();
        socketChannel.read(buffer);

        int resultId = buffer.getInt();
        double resultValue = buffer.getDouble();
        if (results.containsKey(resultId)) {
            Result curRes = results.get(resultId);
            curRes.setResult(resultValue);
            curRes.setState(ClientState.DONE);
        }
    }
}
