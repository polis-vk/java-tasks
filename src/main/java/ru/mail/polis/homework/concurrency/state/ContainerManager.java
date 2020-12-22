package ru.mail.polis.homework.concurrency.state;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Класс, который упраялвет контейнерами и умеет их инициализировать, запускать, финишировать и закрывать.
 * Делает все это параллельно, то есть может использоваться из многих потоков.
 * <p>
 * Это задание на 2 балла за каждый метод. Конструктор на 1 балл
 * Max 11 баллов
 */
public class ContainerManager {
    private final List<CalculateContainer<Double>> calculateContainers;

    /**
     * Создайте список из непустых контейнеров
     */
    public ContainerManager(int containersCount) {
        Random r = new Random();

        this.calculateContainers = Stream.generate(() -> new CalculateContainer<Double>(r.nextDouble()))
                .limit(containersCount)
                .collect(Collectors.toList());
    }

    /**
     * Используйте executor c расширяемым количеством потоков,
     * который будет инициировать все контейнеры, какой-нибудь математической операцией 1_000_000 раз.
     * (для этого используйте вспомогательный метод operation)
     * <p>
     * Каждый контейнер надо исполнять отдельно.
     */
    public synchronized void initContainers() {
        ExecutorService e = Executors.newCachedThreadPool();

        for (CalculateContainer<Double> c : this.calculateContainers) {
            e.submit(() -> c.init(operation(k -> k + 20)));
        }

        e.shutdown();
    }

    /**
     * Используйте executor c 2 потоками (общий с операцией finish),
     * который будет запускать все контейнеры какой-нибудь математической операцией 1_000_000 раз
     * (для этого используйте вспомогательный метод operation)
     * <p>
     * Каждый контейнер надо исполнять отдельно.
     */
    public synchronized void runContainers() {
        ExecutorService e = Executors.newFixedThreadPool(2);

        for (CalculateContainer<Double> c : this.calculateContainers) {
            e.submit(() -> c.run(operation(Double::sum), c.getResult()));
        }

        e.shutdown();
    }

    /**
     * Используйте executor c 2 потоками (общий с операцией run), который будет принимать
     * элемент из контейнеров и печатать их с соответствующим текстом об совершенных операциях
     * <p>
     * Каждый контейнер надо исполнять отдельно.
     */
    public synchronized void finishContainers() {
        ExecutorService e = Executors.newFixedThreadPool(2);

        for (CalculateContainer<Double> c : this.calculateContainers) {
            e.submit(() -> {
                c.finish(System.out::println);
                System.out.println("Finished " + c.getClass().toString());
            });
        }

        e.shutdown();
    }

    /**
     * Используйте executor c 1 потоком, который будет принимать элемент из контейнеров
     * и печатать их с соответствующим текстом о закртыии.
     * <p>
     * Каждый контейнер надо исполнять отдельно.
     * <p>
     * Так как этот метод переводит контейнер в закрытое состояине,
     * то нужно добавить некоторую синхронизацию, которая разблокируется,
     * как только закроются все 10 контейеров
     */
    public synchronized void closeContainers() {
        ExecutorService e = Executors.newSingleThreadExecutor();

        for (CalculateContainer<Double> c : this.calculateContainers) {
            e.submit(() -> {
                c.close(System.out::println);
                System.out.println("Closed " + c.getClass().toString());
            });
        }

        e.shutdown();
    }

    /**
     * Этот метод должен ждать, пока все контейнеры не закроются или пока не закончится время.
     * Если вышло время, то метод должен вернуть false, иначе true.
     * <p>
     * Почти все методы ожидания, которые реализованы в Java умеют ждать с таймаутом.
     * Учтите, что время передается в милисекундах.
     */
    public boolean await(long timeoutMillis) throws Exception {
        Thread.sleep(timeoutMillis);

        return calculateContainers.stream().map(c -> c.getState().equals(State.CLOSE)).reduce(true, (acc, v) -> acc && v);
    }

    public List<CalculateContainer<Double>> getCalculateContainers() {
        return calculateContainers;
    }

    private <T> UnaryOperator<T> operation(UnaryOperator<T> operator) {
        return param -> {
            T result = param;

            for (int i = 0; i < 1000; i++) {
                result = operator.apply(result);
            }
            return result;
        };
    }

    private <T> BinaryOperator<T> operation(BinaryOperator<T> operator) {
        return (start, delta) -> {
            T result = start;
            for (int i = 0; i < 1000; i++) {
                result = operator.apply(result, delta);
            }
            return result;
        };
    }

}
