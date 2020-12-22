package ru.mail.polis.homework.concurrency.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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

    private final List<CalculateContainer<Double>> calculateContainers = new ArrayList<>();

    private final ExecutorService initExecutor = Executors.newCachedThreadPool();
    private final ExecutorService runExecutor = Executors.newFixedThreadPool(2);
    private final ExecutorService finishExecutor = Executors.newFixedThreadPool(2);
    private final ExecutorService closeExecutor = Executors.newSingleThreadExecutor();
    private final CountDownLatch countDownLatch;
    private static final Random random = new Random();

    /**
     * Создайте список из непустых контейнеров
     */
    public ContainerManager(int containersCount) {
        for (int i = 0; i < containersCount; i++) {
            this.calculateContainers.add(new CalculateContainer<>(random.nextDouble()));
        }
        this.countDownLatch = new CountDownLatch(containersCount);
    }


    /**
     * Используйте executor c расширяемым количеством потоков,
     * который будет инициировать все контейнеры, какой-нибудь математической операцией 1_000_000 раз.
     * (для этого используйте вспомогательный метод operation)
     *
     * Каждый контейнер надо исполнять отдельно.
     */
    public void initContainers() {
        for (CalculateContainer<Double> calculateContainer : calculateContainers) {
                initExecutor.execute(() -> calculateContainer.init(operation(Math::cos)));

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
        for (CalculateContainer<Double> calculateContainer : calculateContainers) {
                runExecutor.execute(() -> calculateContainer.run(operation(Math::hypot), random.nextDouble()));
        }
    }


    /**
     * Используйте executor c 2 потоками (общий с операцией run), который будет принимать
     * элемент из контейнеров и печатать их с соответствующим текстом об совершенных операциях
     *
     * Каждый контейнер надо исполнять отдельно.
     */
    public void finishContainers() {
        for (CalculateContainer<Double> calculateContainer : calculateContainers) {
            System.out.print("Result of container is ");
            finishExecutor.execute(() -> calculateContainer.finish(System.out::println));
            System.out.println();
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
    public void closeContainers() throws BrokenBarrierException, InterruptedException {
        for (CalculateContainer<Double> calculateContainer : calculateContainers) {
            closeExecutor.execute(() -> calculateContainer.close(x -> {
                countDownLatch.countDown();
                System.out.println("Container is closed. Value is " + x);
            }));
            System.out.println();
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