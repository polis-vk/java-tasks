package ru.mail.polis.homework.concurrency.state;

import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * Класс, который упраялвет контейнерами и умеет их инициализировать, запускать, финишировать и закрывать.
 * Делает все это параллельно, то есть может использоваться из многих потоков.
 *
 * Это задание на 2 балла за каждый метод. Конструктор на 1 балл
 * Max 11 баллов
 */
public class ContainerManager {

    private final List<CalculateContainer<Double>> calculateContainers;
    private final CountDownLatch countDownLatch = new CountDownLatch(2);
    private final ExecutorService service = Executors.newFixedThreadPool(2);

    /**
     * Создайте список из непустых контейнеров
     */
    public ContainerManager(int numberOfContainers) {
        this.calculateContainers = Stream
                .generate(() -> new CalculateContainer<>(10d))
                .limit(2)
                .collect(Collectors.toList());
    }


    /**
     * используйте executor c расширяемым количеством потоков,
     * который будет инициировать все контейнеры, какой-нибудь математической операцией 1_000_000 раз.
     * (для этого используйте вспомогательный метод operation)
     *
     * Каждый контейнер надо исполнять отдельно.
     */
    public void initContainers() {
        final ExecutorService serviceInit = Executors.newCachedThreadPool();
        for (CalculateContainer<Double> container : calculateContainers) {
            for (int j = 0; j < 20; j++) {
                serviceInit.execute(() -> container.init(operation(Math::sqrt)));
            }
        }
    }


    /**
     * используйте executor c 2 потоками (общий с операцией finish),
     * который будет запускать все контейнеры какой-нибудь математической операцией 1_000_000 раз
     * (для этого используйте вспомогательный метод operation)
     *
     * Каждый контейнер надо исполнять отдельно.
     */
    public void runContainers() {
        final ExecutorService service = Executors.newFixedThreadPool(2);
        for (CalculateContainer<Double> container : calculateContainers) {
            for (int i = 0; i < 20; i++) {
                service.execute(() -> container.run(operation((start, param) -> start + param), 5d));
            }
        }
    }


    /**
     * используйте executor c 2 потоками (общий с операцией run), который будет принимать
     * элемент из контейнеров и печатать их с соответствующим текстом об совершенных операциях
     *
     * Каждый контейнер надо исполнять отдельно.
     */
    public void finishContainers() {
        final ExecutorService service = Executors.newFixedThreadPool(2);
        for (CalculateContainer<Double> container : calculateContainers) {
            service.execute(() -> container.finish(value -> {
                System.out.println("finish " + value);
            }));
        }
    }


    /**
     * используйте executor c 1 потоком, который будет принимать элемент из контейнеров
     * и печатать их с соответствующим текстом о закртыии.
     *
     * Каждый контейнер надо исполнять отдельно.
     *
     * Так как этот метод переводит контейнер в закрытое состояине,
     * то нужно добавить некоторую синхронизацию, которая разблокируется,
     * как только закроются все 10 контейеров
     */
    public void closeContainers() {
        ExecutorService serviceClose = Executors.newFixedThreadPool(1);
        for (CalculateContainer<Double> container : calculateContainers) {
            serviceClose.execute(() -> container.close(value -> {
                countDownLatch.countDown();
                System.out.println("close " + value);
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
