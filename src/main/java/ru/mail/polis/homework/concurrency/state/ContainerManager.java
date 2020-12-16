package ru.mail.polis.homework.concurrency.state;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;


/**
 * Класс, который упраялвет контейнерами и умеет их инициализировать, запускать, финишировать и закрывать.
 * Делает все это параллельно, то есть может использоваться из многих потоков.
 *
 * Это задание на 2 балла за каждый метод. Конструктор на 1 балл
 * Max 11 баллов
 */
public class ContainerManager {

    private final List<CalculateContainer<Double>> calculateContainers;
    private CountDownLatch countDownLatch;

    private static final int N = 1000000;
    private static final Random random = new Random();

    private ExecutorService runFinishThreadPool = Executors.newFixedThreadPool(2);

    /**
     * Создайте список из непустых контейнеров
     */
    public ContainerManager(int containersCount) {
        this.calculateContainers = new CopyOnWriteArrayList<>();
        for (int i = 0; i < containersCount; i++) {
            calculateContainers.add(new CalculateContainer<>(random.nextDouble()));
        }
        System.out.println(calculateContainers);
        countDownLatch = new CountDownLatch(containersCount);
    }


    /**
     * Используйте executor c расширяемым количеством потоков,
     * который будет инициировать все контейнеры, какой-нибудь математической операцией 1_000_000 раз.
     * (для этого используйте вспомогательный метод operation)
     *
     * Каждый контейнер надо исполнять отдельно.
     */
    public void initContainers() {
        ExecutorService threadPool = Executors.newCachedThreadPool();
        UnaryOperator<Double> operation = operation(Math::sin);
        for (CalculateContainer<Double> container : calculateContainers) {
            for (int i = 0; i < N; i++) {
                threadPool.execute(() -> container.init(operation));
            }
        }
    }


    /**
     * Используйте executor c 2 потоками (общий с операцией finish),
     * который будет запускать все контейнеры какой-нибудь математической операцией 1_000_000 раз
     * (для этого используйте вспомогательный метод operation)
     *
     * Каждый контейнер надо исполнять отдельно.
     */
    public void runContainers() {
        BinaryOperator<Double> operation = operation((a, b) -> Math.sin((a + b)));
        for (CalculateContainer<Double> container : calculateContainers) {
            for (int i = 0; i < N; i++) {
                runFinishThreadPool.execute(() -> container.run(operation, random.nextDouble()));
            }
        }
    }


    /**
     * Используйте executor c 2 потоками (общий с операцией run), который будет принимать
     * элемент из контейнеров и печатать их с соответствующим текстом об совершенных операциях
     *
     * Каждый контейнер надо исполнять отдельно.
     */
    public void finishContainers() {
        AtomicReference<Double> result = new AtomicReference<>((double) 0);
        int i = 0;
        for (CalculateContainer<Double> container : calculateContainers) {
            runFinishThreadPool.execute(() -> container.finish(result::set));
            System.out.println(i++ + " container finished  with " + result.get());
        }
    }


    /**
     * Используйте executor c 1 потоком, который будет принимать элемент из контейнеров
     * и печатать их с соответствующим текстом о закртыии.
     *
     * Каждый контейнер надо исполнять отдельно.
     *
     * Так как этот метод переводит контейнер в закрытое состояине,
     * то нужно добавить некоторую синхронизацию, которая разблокируется,
     * как только закроются все 10 контейеров
     */
    public void closeContainers() throws InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(1);
        AtomicReference<Double> result = new AtomicReference<>((double) 0);
        int i = 0;
        for (CalculateContainer<Double> container : calculateContainers) {
            threadPool.execute(() -> container.close(closedValue -> {
                countDownLatch.countDown();
                result.set(closedValue);
            }));
            System.out.println(i++ + " container closed with " + result.get());
        }
        countDownLatch.await();
    }

    /**
     * Этот метод должен ждать, пока все контейнеры не закроются или пока не закончится время.
     * Если вышло время, то метод должен вернуть false, иначе true.
     *
     * Почти все методы ожидания, которые реализованы в Java умеют ждать с таймаутом.
     * Учтите, что время передается в милисекундах.
     */
    public boolean await(long timeoutMillis) throws Exception {
        return countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
    }

    public List<CalculateContainer<Double>> getCalculateContainers() {
        return calculateContainers;
    }

    private  <T> UnaryOperator<T> operation(UnaryOperator<T> operator) {
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
