package ru.mail.polis.homework.concurrency.state;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;
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
    private final CountDownLatch countDownLatch = new CountDownLatch(5);
    /**
     * Создайте список из непустых контейнеров
     */
    public ContainerManager(int containersCount) {
        this.calculateContainers = Arrays.asList(
                new CalculateContainer<>(1d),
                new CalculateContainer<>(2d),
                new CalculateContainer<>(3d),
                new CalculateContainer<>(4d),
                new CalculateContainer<>(5d)
        );
    }


    /**
     * Используйте executor c расширяемым количеством потоков,
     * который будет инициировать все контейнеры, какой-нибудь математической операцией 1_000_000 раз.
     * (для этого используйте вспомогательный метод operation)
     *
     * Каждый контейнер надо исполнять отдельно.
     */
    public void initContainers() {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (CalculateContainer container : calculateContainers) {

                executorService.execute(() -> container.init(operation(Math::exp)));

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
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (CalculateContainer<Double> container : calculateContainers) {

                executorService.execute(() -> container.run(operation((x, y) -> x * y), 12.28));

        }
    }


    /**
     * Используйте executor c 2 потоками (общий с операцией run), который будет принимать
     * элемент из контейнеров и печатать их с соответствующим текстом об совершенных операциях
     *
     * Каждый контейнер надо исполнять отдельно.
     */
    public void finishContainers() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (CalculateContainer container : calculateContainers) {
            executorService.execute(() -> container.finish(value -> {
                System.out.println("This is finish " + value);
            }));
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
    public void closeContainers() {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        for (CalculateContainer containerManager : calculateContainers) {
             executorService.execute(() -> containerManager.close(value -> {
                 System.out.println("Close with value " + value);
             }));
             while (containerManager.getState() != State.CLOSE) {}
             countDownLatch.countDown();
        }
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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
