package ru.mail.polis.homework.concurrency.state;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;


/**
 * Класс, который упраялвет контейнерами и умеет их инициализировать, запускать, финишировать и закрывать.
 * Делает все это параллельно, то есть может использоваться из многих потоков.
 * <p>
 * Это задание на 2 балла за каждый метод. Конструктор на 1 балл
 * Max 11 баллов
 */
public class ContainerManager {

    private final List<CalculateContainer<Double>> calculateContainers;

    private final CyclicBarrier cyclicBarrier;

    private final ExecutorService initExecutor = Executors.newCachedThreadPool();
    private final ExecutorService twoThreadExecutor = Executors.newFixedThreadPool(2);
    private final ExecutorService closeExecutor = Executors.newSingleThreadExecutor();
    private final ExecutorService waitExecutor = Executors.newCachedThreadPool();

    /**
     * Создайте список из непустых контейнеров
     */
    public ContainerManager(int containersCount) {
        calculateContainers = new ArrayList<>(containersCount);

        for (int i = 0; i < containersCount; i++) {
            calculateContainers.add(new CalculateContainer<>(10d));
        }

        cyclicBarrier = new CyclicBarrier(containersCount);
    }


    /**
     * Используйте executor c расширяемым количеством потоков,
     * который будет инициировать все контейнеры, какой-нибудь математической операцией 1_000_000 раз.
     * (для этого используйте вспомогательный метод operation)
     * <p>
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
     * <p>
     * Каждый контейнер надо исполнять отдельно.
     */
    public void runContainers() {
        for (CalculateContainer<Double> calculateContainer : calculateContainers) {
            twoThreadExecutor.execute(() -> calculateContainer.run(operation((x, y) -> x + y), 10d));
        }
    }


    /**
     * Используйте executor c 2 потоками (общий с операцией run), который будет принимать
     * элемент из контейнеров и печатать их с соответствующим текстом об совершенных операциях
     * <p>
     * Каждый контейнер надо исполнять отдельно.
     */
    public void finishContainers() {
        for (CalculateContainer<Double> calculateContainer : calculateContainers) {
            twoThreadExecutor.execute(() -> calculateContainer.finish(value -> System.out.println("finish " + value)));
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
    public void closeContainers() {
        for (CalculateContainer<Double> calculateContainer : calculateContainers) {
            closeExecutor.execute(() -> calculateContainer.close(x -> System.out.println(x + " was closed")));
        }

        try {
            cyclicBarrier.await();

            initExecutor.shutdown();
            twoThreadExecutor.shutdown();
            closeExecutor.shutdown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }
    }

    /**
     * Этот метод должен ждать, пока все контейнеры не закроются или пока не закончится время.
     * Если вышло время, то метод должен вернуть false, иначе true.
     * <p>
     * Почти все методы ожидания, которые реализованы в Java умеют ждать с таймаутом.
     * Учтите, что время передается в милисекундах.
     */
    public boolean await(long timeoutMillis) throws Exception {

        boolean allClosed = true;

        boolean result = waitExecutor.awaitTermination(timeoutMillis, TimeUnit.MILLISECONDS);

        for (CalculateContainer<Double> calculateContainer : calculateContainers) {
            if (waitExecutor.submit(calculateContainer::getState).get() != State.CLOSE) {
                allClosed = false;
            }
        }

        if (allClosed && cyclicBarrier.getNumberWaiting() == 0) {
            waitExecutor.shutdown();
            return true;
        }

        return result;
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
