package ru.mail.polis.homework.concurrency.state;

import java.util.List;
import java.util.concurrent.*;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
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
    private static final int COUNT = 10000;
    final CountDownLatch countDownLatch;
    private final ExecutorService fixedExecutorService = Executors.newFixedThreadPool(2);
    ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * Создайте список из непустых контейнеров
     */
    public ContainerManager(int containersCount) {
        this.calculateContainers = Stream.generate(() -> new CalculateContainer<Double>(1.0))
                .limit(containersCount)
                .collect(Collectors.toList());
        countDownLatch = new CountDownLatch(containersCount);
    }


    /**
     * Используйте executor c расширяемым количеством потоков,
     * который будет инициировать все контейнеры, какой-нибудь математической операцией 1_000_000 раз.
     * (для этого используйте вспомогательный метод operation)
     * <p>
     * Каждый контейнер надо исполнять отдельно.
     */
    public void initContainers() {
        for(CalculateContainer<Double> container : calculateContainers) {
            executorService.execute(() -> container.init(operation(Math::sin)));
        }
    }


    /**
     * Используйте executor c 2 потоками (общий с операцией finish),
     * который будет запускать все контейнеры какой-нибудь математической операцией 1_000_000 раз
     * (для этого используйте вспомогательный метод operation)
     * <p>
     * Каждый контейнер надо исполнять отдельно.
     */
    public void runContainers() {
        for (CalculateContainer<Double> container : calculateContainers) {
            fixedExecutorService.execute(() -> container.run(operation(Double::sum), 2.0));
        }
    }


    /**
     * Используйте executor c 2 потоками (общий с операцией run), который будет принимать
     * элемент из контейнеров и печатать их с соответствующим текстом об совершенных операциях
     * <p>
     * Каждый контейнер надо исполнять отдельно.
     */
    public void finishContainers() {
        for (CalculateContainer<Double> container : calculateContainers) {
            fixedExecutorService.execute(() -> container.finish(value -> System.out.println("Finish " + value)));
        }
    }


    /**
     * Используйте executor c 1 потоком, который будет принимать элемент из контейнеров
     * и печатать их с соответствующим текстом о закрытии.
     * <p>
     * Каждый контейнер надо исполнять отдельно.
     * <p>
     * Так как этот метод переводит контейнер в закрытое состояние,
     * то нужно добавить некоторую синхронизацию, которая разблокируется,
     * как только закроются все 10 контейнеров
     */
    public void closeContainers() throws InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (CalculateContainer<Double> e : calculateContainers) {
            executorService.execute(() -> {
                e.close(value -> System.out.println("Close " + value));
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        fixedExecutorService.shutdown();
        this.executorService.shutdown();
    }

    /**
     * Этот метод должен ждать, пока все контейнеры не закроются или пока не закончится время.
     * Если вышло время, то метод должен вернуть false, иначе true.
     * <p>
     * Почти все методы ожидания, которые реализованы в Java умеют ждать с таймаутом.
     * Учтите, что время передается в милисекундах.
     */
    public boolean await(long timeoutMillis) throws Exception {
        return countDownLatch.await(timeoutMillis, TimeUnit.MILLISECONDS);
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
