package ru.mail.polis.homework.concurrency.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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

    private final String END_POINT = "END_POINT";

    private final String ADDRESS = "localhost";
    private final AtomicInteger currentChannel = new AtomicInteger(0);
    private final AtomicInteger currentTask = new AtomicInteger(0);
    private final int[] clientsPort;
    private final int serverPort;
    private final int threadsCountForSend;
    private SocketChannel[] channels;
    private List<Operand> tasks;

    /**
     * @param clientsPort         массив портов для отправки
     * @param serverPort          порт для принятия
     * @param threadsCountForSend максимальное кол-во потоков, которое можно использовать.
     *                            Дано, что threadsCountForSend >= clientsPort.length
     */
    public Client(int[] clientsPort, int serverPort, int threadsCountForSend) throws IOException {
        this.clientsPort = clientsPort;
        this.serverPort = serverPort;
        this.threadsCountForSend = threadsCountForSend;
    }

    /**
     * Отправляет на сервер операции в несколько потоков. Плюс отправляет порт, на котором будет слушать ответ.
     * Важно, не потеряйте порядок операндов
     * Возвращает Result с отложенным заполнением ответа.
     */
    public Result calculate(List<Operand> operands) throws IOException {
        openSockets();
        tasks = operands;

        ExecutorService service = Executors.newFixedThreadPool(threadsCountForSend);
        service.execute(new Sender());
        service.shutdown();
        try {
            service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channels[0].write(ByteBuffer.wrap(END_POINT.getBytes()));

        return null;
    }

    /**
     * Возвращает результат, для заданной айдишки запроса
     */
    public Result getResult(int id) {
        return null;
    }

    /**
     * Закрывает клиента и всего его запросы.
     */
    public void close() {

    }

    private void openSockets() {
        channels = new SocketChannel[clientsPort.length];
        try {
            for (int i = 0; i < clientsPort.length; i++) {
                SocketChannel client = SocketChannel.open();
                client.bind(new InetSocketAddress(ADDRESS, clientsPort[i]));
                client.connect(new InetSocketAddress(serverPort));
                client.configureBlocking(false);
                channels[i] = client;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class Sender implements Runnable {
        @Override
        public void run() {
            try {
                while (currentTask.get() < tasks.size()) {
                    channels[currentChannel.get() % channels.length]
                            .write(ByteBuffer.wrap(tasks
                                    .get(currentTask.getAndIncrement()).serialization().getBytes()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
