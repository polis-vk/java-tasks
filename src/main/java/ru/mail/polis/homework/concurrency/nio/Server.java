package ru.mail.polis.homework.concurrency.nio;

import java.io.IOException;
import java.io.StringReader;
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
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.UnaryOperator;

import com.fasterxml.jackson.databind.ObjectMapper;

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

    private final String END_POINT = "END_POINT";

    private Operand[] ops;
    private boolean stopRead = false;
    private final int[] serverPorts;
    private final int calculateThreadsCount;
    private final ByteBuffer buffer = ByteBuffer.allocate(256);
    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Operand> queueOps = new LinkedBlockingQueue<>();
    /*private final Map<OperandType, UnaryOperator<Double>> m = Map.of(null, i -> i,
            OperandType.SIN, Math::sin,
            OperandType.COS, Math::cos,
            OperandType.EXP,
            OperandType.ABS, Math::abs);*/


    public Server(int[] serverPorts, int calculateThreadsCount) throws IOException {
        this.serverPorts = serverPorts;
        this.calculateThreadsCount = calculateThreadsCount;
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

    public void start() {
        try {
            Thread reader = new Thread(new Reader());
            reader.start();
            reader.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        toObjects();

        double result = calculate();
    }

    private void toObjects() {
        StringReader reader;
        ObjectMapper mapper = new ObjectMapper();
        Operand current;
        int index = 0;
        for (String s : queue) {
            try {
                reader = new StringReader(s);
                current = mapper.readValue(reader, Operand.class);
                current.setPosition(index++);
                queueOps.add(current);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private double calculate() {
        ops = new Operand[queueOps.size()];
        ExecutorService service = Executors.newFixedThreadPool(calculateThreadsCount);
        //service.execute();
        return 0;
    }

    private class Reader implements Runnable {
        @Override
        public void run() {
            try (Selector selector = Selector.open()) {
                for (int serverPort : serverPorts) {
                    ServerSocketChannel serverSocket = ServerSocketChannel.open();
                    serverSocket.bind(new InetSocketAddress(serverPort));
                    serverSocket.configureBlocking(false);
                    serverSocket.register(selector, SelectionKey.OP_ACCEPT);
                }

                while (!stopRead) {
                    selector.select();
                    Set<SelectionKey> selectedKeys = selector.selectedKeys();
                    Iterator<SelectionKey> iter = selectedKeys.iterator();
                    while (iter.hasNext()) {

                        SelectionKey key = iter.next();
                        if (key.isAcceptable()) {
                            accept(key, selector);
                        }

                        if (key.isReadable()) {
                            read(key);
                        }
                        iter.remove();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void accept(SelectionKey key, Selector selector) throws IOException {
            SocketChannel client = ((ServerSocketChannel) key.channel()).accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        }

        private void read(SelectionKey key) {
            try {
                SocketChannel client = (SocketChannel) key.channel();
                client.read(buffer);
                String str = new String(buffer.array()).trim();
                if (str.contains(END_POINT) && buffer.position() == END_POINT.length()) {
                    stopRead = true;
                    return;
                }

                System.out.println("Object: " + str);
                queue.add(str);
                buffer.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private class Calculator implements Runnable {

        Operand current;

        @Override
        public void run() {
            while ((current = queueOps.poll()) != null) {
                ops[current.getPosition()] = null;
            }
        }
    }
}
