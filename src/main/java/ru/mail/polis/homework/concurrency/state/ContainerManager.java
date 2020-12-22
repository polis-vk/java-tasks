package ru.mail.polis.homework.concurrency.state;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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

    private final CountDownLatch latch;

    private final ExecutorService executorRunFinish = Executors.newFixedThreadPool(2);
    private final ExecutorService executorInit = Executors.newCachedThreadPool();

    /**
     * Создайте список из непустых контейнеров
     */
    public ContainerManager(int containersCount) {
        this.calculateContainers = Stream.generate(() -> new CalculateContainer<>(5d))
            .limit(containersCount)
            .collect(Collectors.toList());
        this.latch = new CountDownLatch(containersCount);
    }


    /**
     * Используйте executor c расширяемым количеством потоков,
     * который будет инициировать все контейнеры, какой-нибудь математической операцией 1_000 раз.
     * (для этого используйте вспомогательный метод operation)
     * <p>
     * Каждый контейнер надо исполнять отдельно.
     */
    public void initContainers() {
        for (CalculateContainer<Double> container : calculateContainers) {
            executorInit.execute(() -> container.init(operation(Math::sqrt)));
        }
    }


    /**
     * Используйте executor c 2 потоками (общий с операцией finish),
     * который будет запускать все контейнеры какой-нибудь математической операцией 1_000 раз
     * (для этого используйте вспомогательный метод operation)
     * <p>
     * Каждый контейнер надо исполнять отдельно.
     */
    public void runContainers() {
        for (CalculateContainer<Double> container : calculateContainers) {
            executorRunFinish.execute(() -> container.run(operation(Double::sum), 5d));
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
            executorRunFinish.execute(() -> container.finish(value -> System.out.println("finish " + value)));
        }
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
    public void closeContainers() throws InterruptedException {
        ExecutorService executorClose = Executors.newSingleThreadExecutor();
        for (CalculateContainer<Double> container : calculateContainers) {
            executorClose.execute(() -> container.close(value -> {
                System.out.println("close " + value);
                latch.countDown();
            }));
        }
        latch.await();
        executorClose.shutdown();
        executorRunFinish.shutdown();
        executorInit.shutdown();
    }

    /**
     * Этот метод должен ждать, пока все контейнеры не закроются или пока не закончится время.
     * Если вышло время, то метод должен вернуть false, иначе true.
     * <p>
     * Почти все методы ожидания, которые реализованы в Java умеют ждать с таймаутом.
     * Учтите, что время передается в милисекундах.
     */
    public boolean await(long timeoutMillis) throws Exception {
        return latch.await(timeoutMillis, TimeUnit.MILLISECONDS);
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
