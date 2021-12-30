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
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
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
    private final String ADDRESS = "localhost";

    private final AtomicInteger counter = new AtomicInteger(0);
    private final int[] serverPorts;
    private final int calculateThreadsCount;
    private final ByteBuffer buffer = ByteBuffer.allocate(256);
    private final List<Integer> id = new CopyOnWriteArrayList<>();
    private final Map<Integer, Double> resultMap = new ConcurrentHashMap<>();
    private final Map<OperandType, UnaryOperator<Double>> mapSelfOperations = Map.of(OperandType.SIN, Math::sin,
            OperandType.COS, Math::cos,
            OperandType.EXP, i -> Math.pow(Math.E, i),
            OperandType.ABS, Math::abs,
            OperandType.TAN, Math::tan,
            OperandType.SQUARE, Math::sqrt,
            OperandType.LN, Math::log);

    public Server(int[] serverPorts, int calculateThreadsCount) throws IOException {
        this.serverPorts = serverPorts;
        this.calculateThreadsCount = calculateThreadsCount;
    }

    /**
     * Можно редактировать метод. Должен вернуть всех клиентов
     */
    public List<Integer> getClients() {
        return id;
    }

    /**
     * Можно редактировать метод. Должен вернуть все операции для заданного клиента.
     * Если клиента нет или он уже закрыт -- вернуть null. Если клиент есть, но операций нет -- вернуть пустой список.
     */
    public List<ServerOperation> getOperationsForClient() {
        return null;
    }

    public void start() {
        Thread[] readers = new Thread[serverPorts.length];

        for (int i = 0; i < readers.length; i++) {
            readers[i] = new Thread(new Reader(serverPorts[i]));
            readers[i].start();
        }

        Thread send = new Thread(new Sender());
        send.start();
    }

    private class Reader implements Runnable {

        int currentPort;
        private boolean isFirst = true;
        private int resultPort;
        private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();
        private final BlockingQueue<Operand> queueOps = new LinkedBlockingQueue<>();

        public Reader(int currentPort) {
            this.currentPort = currentPort;
        }

        @Override
        public void run() {
            try (Selector selector = Selector.open()) {
                ServerSocketChannel serverSocket = ServerSocketChannel.open();
                serverSocket.bind(new InetSocketAddress(currentPort));
                serverSocket.configureBlocking(false);
                serverSocket.register(selector, SelectionKey.OP_ACCEPT);

                while (true) {
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
            ServerSocketChannel sSocket = (ServerSocketChannel) key.channel();
            SocketChannel client = sSocket.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
        }

        private void read(SelectionKey key) {
            try {
                SocketChannel client = (SocketChannel) key.channel();
                client.read(buffer);
                String str = new String(buffer.array()).trim();

                if (isFirst) {
                    int idx = 0;
                    while (idx != str.length()) {
                        if (str.charAt(idx) == '|') {
                            break;
                        }
                        idx++;
                    }

                    resultPort = Integer.parseInt(str.substring(0, idx));
                    id.add(Integer.valueOf(str.substring(idx + 1)));
                    isFirst = false;
                    buffer.clear();
                    return;
                }

                if (str.contains(END_POINT) && buffer.position() == END_POINT.length()) {
                    buffer.clear();
                    toObjects();
                    calculate();
                    return;
                }

                queue.add(str);
                buffer.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }

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

        private void calculate() {
            Operand[] ops = new Operand[queueOps.size()];
            ExecutorService service = Executors.newFixedThreadPool(calculateThreadsCount);
            service.execute(new Calculator(ops, queueOps));
            service.shutdown();
            try {
                service.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double answer = ops[0].getA();
            for (int i = 1; i < ops.length; i++) {
                switch (ops[i - 1].getOperationSecond()) {
                    case PLUS:
                        answer += ops[i].getA();
                        break;
                    case MINUS:
                        answer -= ops[i].getA();
                        break;
                    case MULT:
                        answer *= ops[i].getA();
                        break;
                    case DIVIDE:
                        try {
                            answer /= ops[i].getA();
                        } catch (ArithmeticException e) {
                            e.printStackTrace();
                        }
                        break;
                }
            }
            resultMap.put(resultPort, answer);
        }
    }


    private class Calculator implements Runnable {

        private final Operand[] ops;
        private final BlockingQueue<Operand> queueOps;

        public Calculator(Operand[] ops, BlockingQueue<Operand> queueOps) {
            this.ops = ops;
            this.queueOps = queueOps;
        }

        @Override
        public void run() {
            Operand current;

            while ((current = queueOps.poll()) != null) {
                if (current.getOperationFirst() != null) {
                    current.setNewA(mapSelfOperations.get(current.getOperationFirst()).apply(current.getA()));
                }
                ops[current.getPosition()] = current;
            }
        }
    }

    private class Sender implements Runnable {

        SocketChannel sender;

        @Override
        public void run() {
            try {
                sender = SocketChannel.open();
                sender.bind(new InetSocketAddress(ADDRESS, 9595));
                while (true) {
                    for (Map.Entry<Integer, Double> current : resultMap.entrySet()) {
                        sender.connect(new InetSocketAddress(current.getKey()));
                        sender.configureBlocking(false);
                        sender.write(ByteBuffer.wrap((current.getValue().toString()).getBytes()));
                        resultMap.remove(current.getKey());
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
