package ru.mail.polis.homework.concurrency.state;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
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
    private static final double ARGUMENT_MIN_VALUE = -100, ARGUMENT_MAX_VALUE = 100;
    private static final int OPERATIONS_REPEAT_COUNT = 1_000_000;

    private final List<CalculateContainer<Double>> calculateContainers;

    private final ExecutorService initExecutor = Executors.newCachedThreadPool();
    private final ExecutorService runFinishExecutor = Executors.newFixedThreadPool(2);
    private final ExecutorService closeExecutor = Executors.newFixedThreadPool(1);

    private final CountDownLatch containersCloseLock;

    private final Random random = new Random();

    /**
     * Создайте список из непустых контейнеров
     */
    public ContainerManager(int containersCount) {
        this.calculateContainers = new ArrayList<>(containersCount);
        for (int i = 0; i < containersCount; i++) {
            double value = random.nextDouble() * (ARGUMENT_MAX_VALUE - ARGUMENT_MIN_VALUE) + ARGUMENT_MIN_VALUE;
            calculateContainers.add(new CalculateContainer<>(value));
        }

        containersCloseLock = new CountDownLatch(containersCount);
    }


    /**
     * Используйте executor c расширяемым количеством потоков,
     * который будет инициировать все контейнеры, какой-нибудь математической операцией 1_000_000 раз.
     * (для этого используйте вспомогательный метод operation)
     *
     * Каждый контейнер надо исполнять отдельно.
     */
    public void initContainers() {
        UnaryOperator<Double> initOperation = operation(Math::cos);
        for (CalculateContainer<Double> container : calculateContainers) {
            for (int i = 0; i < OPERATIONS_REPEAT_COUNT; i++) {
                initExecutor.execute(() -> container.init(initOperation));
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
        BinaryOperator<Double> runOperation = operation((containerValue, value) -> Math.atan(containerValue * value));
        for (CalculateContainer<Double> container : calculateContainers) {
            for (int i = 0; i < OPERATIONS_REPEAT_COUNT; i++) {
                double value = random.nextDouble() * (ARGUMENT_MAX_VALUE - ARGUMENT_MIN_VALUE) + ARGUMENT_MIN_VALUE;
                runFinishExecutor.execute(() -> container.run(runOperation, value));
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
        for (CalculateContainer<Double> container : calculateContainers) {
            runFinishExecutor.execute(() -> container.finish(value -> System.out.printf("Container %s finished with resulting value: %f\n", container, value)));
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
        for (CalculateContainer<Double> container : calculateContainers) {
            closeExecutor.execute(() -> {
                containersCloseLock.countDown();
                System.err.printf("Container %s is closed.\n", container);
            });
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
        return containersCloseLock.await(timeoutMillis, TimeUnit.MILLISECONDS);
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
