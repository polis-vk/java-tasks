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

    private final List<CalculateContainer<Double>> calculateContainers;
    private final int N = 1_000_000;
    private final CountDownLatch countDownLatch;
    private final ExecutorService initThreadPool = Executors.newCachedThreadPool();
    private final ExecutorService doubleThreadPool = Executors.newFixedThreadPool(2);
    private final ExecutorService doubleThreadPool2 = Executors.newFixedThreadPool(2);
    private final ExecutorService singleThreadPool = Executors.newSingleThreadExecutor();

    /**
     * Создайте список из непустых контейнеров
     */
    public ContainerManager(int containersCount) {
        this.calculateContainers = new ArrayList<>(containersCount);
        Random random = new Random(2001);
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
        for (CalculateContainer<Double> container : calculateContainers) {
            for (int i = 0; i < N; i++) {
                initThreadPool.execute(
                        () -> container.init(operation(Math::sin))
                );
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
        for (CalculateContainer<Double> container : calculateContainers) {
            for (int j = 0; j < N; j++) {
                doubleThreadPool.execute(
                        () -> container.run(operation(Double::sum), Math.random() * Math.PI * 1_000)
                );
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
            for (int j = 0; j < N; j++) {
                doubleThreadPool2.execute(
                        () -> container.finish((aDouble) -> System.out.println("Finish: " + aDouble))
                );
            }
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
        for (CalculateContainer<Double> container : calculateContainers) {
            singleThreadPool.execute(() -> {
                container.close((aDouble) -> System.out.println("Closed with: " +  aDouble));
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        System.out.println("Unlocked");
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
