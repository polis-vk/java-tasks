package ru.mail.polis.homework.concurrency.executor;

import org.junit.Assert;
import org.junit.Test;

public class SimpleExecutorTest {

    private final Runnable task = () -> {
        try {
            System.out.println("start Task");
            Thread.sleep(10);
            System.out.println("end Task");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    };

    /**
     * запуск 1 задачи несколько раз с интервалом (должен создаться только 1 поток)
     */
    @Test
    public void oneTask() throws InterruptedException {
        SimpleExecutor executor = new SimpleExecutor();
        Assert.assertEquals(0, executor.getLiveThreadsCount());

        for (int i = 0; i < 100; i++) {
            executor.execute(task);
            Thread.sleep(20);
        }

        Assert.assertEquals(1, executor.getLiveThreadsCount());
        executor.shutdown();
    }
    /**
     * запуск параллельно n - 1 задач несколько раз (должно создаться n - 1 потоков) и задачи должны завершится
     * примерно одновременно
     */
    @Test
    public void severalTask() throws InterruptedException {
        SimpleExecutor executor = new SimpleExecutor();
        Assert.assertEquals(0, executor.getLiveThreadsCount());

        int n = 5;
        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < n; i++) {
                executor.execute(task);
            }
            Thread.sleep(60);
        }
        Assert.assertEquals(n, executor.getLiveThreadsCount());
        executor.shutdown();
    }

    /**
     * запуск параллельно n + m задач несколько раз (должно создаться n потоков) и первые n задач должны завершится
     * примерно одновременно, вторые m задач должны завершиться чуть позже первых n и тоже примерно одновременно
     **/
    @Test
    public void severalTaskAtDifferentTimes() throws InterruptedException {
        int n = 5;
        int m = 3;
        SimpleExecutor executor = new SimpleExecutor(n);
        Assert.assertEquals(0, executor.getLiveThreadsCount());

        Runnable task1 = () -> {
            try {
                System.out.println("start Task1");
                Thread.sleep(20);
                System.out.println("end Task1");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable task2 = () -> {
            try {
                System.out.println("start Task2");
                Thread.sleep(10);
                System.out.println("end Task2");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        for (int j = 0; j < 100; j++) {
            for (int i = 0; i < n; i++) {
                executor.execute(task1);
            }
            for (int i = 0; i < m; i++) {
                executor.execute(task2);
            }
            Thread.sleep(150);
        }
        Assert.assertEquals(5, executor.getLiveThreadsCount());
        executor.shutdown();
    }

}