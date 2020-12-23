package ru.mail.polis.homework.concurrency.state;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;


/**
 * Класс, который управляет контейнерами и умеет их инициализировать, запускать, финишировать и закрывать.
 * Делает все это параллельно, то есть может использоваться из многих потоков.
 * <p>
 * Это задание на 2 балла за каждый метод. Конструктор на 1 балл
 * Max 11 баллов
 */
public class ContainerManager {

    private final List<CalculateContainer<Double>> calculateContainers;
    private final CountDownLatch countDownLatch;
    private final int iterationsCount = 10_000;

    private final ExecutorService cachedExecutor = Executors.newCachedThreadPool();
    private final ExecutorService doubleThreadExecutor = Executors.newFixedThreadPool(2);
    private final ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();

    /**
     * Создайте список из непустых контейнеров
     */
    public ContainerManager(int containersCount) {
        this.calculateContainers = new ArrayList<>(containersCount);
        for (int i = 0; i < containersCount; i++) {
            calculateContainers.add(new CalculateContainer<>(Math.random() * 100D));
        }
        this.countDownLatch = new CountDownLatch(containersCount);
    }


    /**
     * Используйте executor c расширяемым количеством потоков,
     * который будет инициировать все контейнеры, какой-нибудь математической операцией 1_000_000 раз.
     * (для этого используйте вспомогательный метод operation)
     * <p>
     * Каждый контейнер надо исполнять отдельно.
     */
    public void initContainers() {
        for (CalculateContainer<Double> container : calculateContainers) {
            UnaryOperator<Double> operator = operation(Math::sin);
            for (int j = 0; j < iterationsCount; j++) {
                cachedExecutor.execute(() -> {
                    container.init(operator);
                });
            }
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
            BinaryOperator<Double> operator = operation(Double::sum);
            for (int j = 0; j < iterationsCount; j++) {
                doubleThreadExecutor.execute(() -> container.run(operator, Math.random() * 10_000));
            }
        }
    }


    /**
     * Используйте executor c 2 потоками (общий с операцией run), который будет принимать
     * элемент из контейнеров и печатать их с соответствующим текстом об совершенных операциях
     * <p>
     * Каждый контейнер надо исполнять отдельно.
     */
    public void finishContainers() {
        Consumer<Double> consumer = x -> System.out.printf("Received %f", x);
        for (CalculateContainer<Double> container : calculateContainers) {
            doubleThreadExecutor.execute(() -> container.finish(consumer));
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
        Consumer<Double> closeConsumer = x -> System.out.printf("%f has been closed", x);
        for (CalculateContainer<Double> container : calculateContainers) {
            singleThreadExecutor.execute(() -> {
                container.close(closeConsumer);
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("Unlocked");
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
