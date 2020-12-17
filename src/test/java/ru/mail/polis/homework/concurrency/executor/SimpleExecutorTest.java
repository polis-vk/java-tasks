package ru.mail.polis.homework.concurrency.executor;

import org.junit.Assert;
import org.junit.Test;

public class SimpleExecutorTest {
    private final Runnable task = () -> {
        System.out.println("Starting a task with " + Thread.currentThread().getName());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ending the task with " + Thread.currentThread().getName());
    };
    private final int numberOfTasksN = 5;
    private final int numberOfTasksM = 3;
    private final int numberOfExecutions = 5;

    /**
     * 1) запуск 1 задачи несколько раз с интервалом (должен создаться только 1 поток)
     */
    @Test
    public void getLiveThreadsCount_OneTaskWithIntervalSeveralTimes_1() {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        Assert.assertEquals(0, simpleExecutor.getLiveThreadsCount());

        for (int i = 0; i < numberOfExecutions; i++) {
            simpleExecutor.execute(task);
            try {
                Thread.sleep(1100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());
        }
    }
    /**
     * 2) запуск параллельно n - 1 задач несколько раз (должно создаться n - 1 потоков) и задачи должны завершится
     *  * примерно одновременно
     */
    @Test
    public void getLiveThreadsCount_NMinus1TasksInParallelSeveralTimes_n() {
        SimpleExecutor simpleExecutor = new SimpleExecutor();

        for (int i = 0; i < numberOfExecutions; i++) {
            System.out.println("Batch " + i);
            for (int j = 0; j < numberOfTasksN - 1; j++) {
                simpleExecutor.execute(task);
            }
            try {
                Thread.sleep(1100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Assert.assertEquals(numberOfTasksN - 1, simpleExecutor.getLiveThreadsCount());
        }

    }

    /**
     * 3) запуск параллельно n + m задач несколько раз (должно создаться n потоков) и первые n задач должны завершится
     *  * примерно одновременно, вторые m задач должны завершиться чуть позже первых n и тоже примерно одновременно
     **/
    @Test
    public void getLiveThreadsCount_NPlusMTasksInParallelSeveralTimes_n() {
        SimpleExecutor simpleExecutor = new SimpleExecutor();

        for (int i = 0; i < numberOfExecutions; i++) {
            System.out.println("Batch " + i);
            System.out.println("N tasks:");
            for (int j = 0; j < numberOfTasksN; j++) {
                simpleExecutor.execute(task);
            }
            try {
                Thread.sleep(1100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("M tasks:");
            for (int j = 0; j < numberOfTasksM; j++) {
                simpleExecutor.execute(task);
            }
            try {
                Thread.sleep(1100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            Assert.assertEquals(numberOfTasksN, simpleExecutor.getLiveThreadsCount());
        }
    }

}