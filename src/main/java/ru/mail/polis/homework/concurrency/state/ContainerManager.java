package ru.mail.polis.homework.concurrency.state;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;


/**
 * Класс, который упраялвет контейнерами и умеет их инициализировать, запускать, финишировать и закрывать.
 * Делает все это параллельно, то есть может использоваться из многих потоков.
 *
 * Это задание на 2 балла за каждый метод. Конструктор на 1 балл
 * Max 11 баллов
 */
public class ContainerManager {

    private final List<CalculateContainer<Double>> calculateContainers;
    private final CountDownLatch countDownLatch;
    private final int amountOfOperations = 1_000_000;
    private final ExecutorService initExecutor = Executors.newCachedThreadPool();
    private final ExecutorService runAndFinishExecutor = Executors.newFixedThreadPool(2);
    private final ExecutorService closeExecutor = Executors.newSingleThreadExecutor();

    /**
     * Создайте список из непустых контейнеров
     */
    public ContainerManager(int containersCount) {
        countDownLatch = new CountDownLatch(containersCount);
        Random random = new Random();
        this.calculateContainers = DoubleStream.generate(random::nextDouble)
                .boxed()
                .map(CalculateContainer::new)
                .limit(containersCount)
                .collect(Collectors.toList());
    }

    /**
     * Используйте executor c расширяемым количеством потоков,
     * который будет инициировать все контейнеры, какой-нибудь математической операцией 1_000_000 раз.
     * (для этого используйте вспомогательный метод operation)
     *
     * Каждый контейнер надо исполнять отдельно.
     */
    public void initContainers() {
        UnaryOperator<Double> operation = operation(Math::sqrt);
        for (CalculateContainer<Double> container : calculateContainers) {
            for (int i = 0; i < amountOfOperations; i++) {
                initExecutor.execute(() -> container.init(operation));
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
        BinaryOperator<Double> operation = operation(Math::pow);
        for (CalculateContainer<Double> container : calculateContainers) {
            for (int i = 0; i < amountOfOperations; i++) {
                runAndFinishExecutor.execute(() -> container.run(operation, 2d));
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
            runAndFinishExecutor.execute(() -> container.finish(item -> System.out.println("finished with result: " + item)));
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
            closeExecutor.execute(() -> container.close(result -> {
                countDownLatch.countDown();
                System.out.println("closed with result: " + result);
            }));
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
                System.out.println("Success operation. Res: " + result);
            }
            return result;
        };
    }

    private <T> BinaryOperator<T> operation(BinaryOperator<T> operator) {
        return (start, delta) -> {
            T result = start;
            for (int i = 0; i < 1000; i++) {
                result = operator.apply(result, delta);
                System.out.println("Success operation. Res: " + result);
            }
            return result;
        };
    }
}