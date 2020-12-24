package ru.mail.polis.homework.concurrent.executor;

import org.junit.Test;
import ru.mail.polis.homework.concurrency.executor.SimpleExecutor;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;

public class SimpleExecutorTest {
    private final long commandSleepTime = 100;
    private final AtomicInteger numOfTask = new AtomicInteger(0);
    private final Runnable command = () -> {
        int numOfThisThreadTask = numOfTask.incrementAndGet();
        System.out.println(numOfThisThreadTask + " task of " + Thread.currentThread().getName()
                + " is start");
        try {
            Thread.sleep(commandSleepTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(numOfThisThreadTask + " task of " + Thread.currentThread().getName()
                + " is finish");
    };

    /**
     * 1) запуск 1 задачи несколько раз с интервалом (должен создаться только 1 поток)
     */
    @Test
    public void test1() {
        SimpleExecutor executor = new SimpleExecutor(2);
        for (int i = 0; i < 10; i++) {
            executor.execute(command);
            try {
                Thread.sleep(2 * commandSleepTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertEquals(executor.getLiveThreadsCount(), 1);
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2) запуск параллельно n - 1 задач несколько раз (должно создаться n - 1 потоков) и задачи должны завершится
     * примерно одновременно
     */
    @Test
    public void test2() {
        int tasksAmount = 10;
        SimpleExecutor executor = new SimpleExecutor(tasksAmount);
        for (int i = 0; i < 10; i++) {
            System.out.println("---" + (i+1) + " iteration---");
            for (int j = 0; j < tasksAmount; j++) {
                executor.execute(command);
            }
            try {
                Thread.sleep(commandSleepTime * 2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        assertEquals(tasksAmount, executor.getLiveThreadsCount());
    }

    /**
     * 3) запуск параллельно n + m задач несколько раз (должно создаться n потоков) и первые n задач должны завершится
     * примерно одновременно, вторые m задач должны завершиться чуть позже первых n и тоже примерно одновременно
     * Max 6 баллов
     */
    @Test
    public void test3() {
        int tasksAmount1 = 10;
        int tasksAmount2 = 15;
        SimpleExecutor executor = new SimpleExecutor(tasksAmount1);
        for (int i = 0; i < 10; i++) {
            System.out.println("---" + (i+1) + " iteration---");
            System.out.println();
            for (int j = 0; j < tasksAmount1; j++) {
                executor.execute(command);
            }
            System.out.println();
            for (int j = 0; j < tasksAmount2; j++) {
                executor.execute(command);
            }
            try {
                Thread.sleep(commandSleepTime * (tasksAmount2+1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertEquals(tasksAmount1, executor.getLiveThreadsCount());
        }
    }
}
