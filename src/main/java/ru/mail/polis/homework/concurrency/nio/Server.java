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
import java.util.Optional;
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
    private static final int BUFFER_SIZE = 256;
    private static final String HOSTNAME = "localhost";

    private final int[] serverPorts;
    private final ExecutorService executorService;

    public Server(int[] serverPorts, int calculateThreadsCount) throws IOException {
        this.serverPorts = serverPorts;
        executorService = Executors.newFixedThreadPool(calculateThreadsCount);

        Thread listener = createListener(serverPorts);
        listener.start();
        executorService.submit(listener);
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

    private Thread createListener(int[] serverPorts) {
        return new Thread(() -> {
            try (Selector selector = Selector.open()) {
                for (int serverPort : serverPorts) {
                    try (ServerSocketChannel serverSocket = ServerSocketChannel.open()) {
                        serverSocket.bind(new InetSocketAddress(HOSTNAME, serverPort));
                        serverSocket.configureBlocking(false);
                        serverSocket.register(selector, SelectionKey.OP_ACCEPT);
                    }
                }

                while (true) {
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

        SocketChannel client = (SocketChannel) key.channel();
        client.read(buffer);
        executorService.submit(new Calculator(getOperand(buffer.array())));
        buffer.clear();
    }

    private Operand getOperand(byte[] buffer) {
        //TODO: Дописать
        return null;
    }

    private class Calculator implements Runnable {
        private final Operand operand;

        public Calculator(Operand operand) {
            this.operand = operand;
        }

        @Override
        public void run() {
            // TODO: Вычислить и отправить
        }
    }
}
