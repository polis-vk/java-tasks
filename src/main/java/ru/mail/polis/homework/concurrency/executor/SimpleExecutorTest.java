package ru.mail.polis.homework.concurrency.executor;

import org.testng.annotations.Test;

import static org.junit.Assert.*;

public class SimpleExecutorTest {

    private static final int RUNNABLE_DELAY = 100;
    private static final int SYNCHRONIZE_DELAY = RUNNABLE_DELAY * 3;

    private Runnable getRunnableWithDelay(String jobName) {
        return () -> {
            System.out.println("Doing job " + jobName +": " + Thread.currentThread().getName());
            try {
                Thread.sleep(RUNNABLE_DELAY);
            } catch (InterruptedException ignored) { }
        };
    }

    /*
    Запуск 1 задачи несколько раз с интервалом (должен создаться только 1 поток)
    */
    @Test
    public void threadsAmount() {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        for (int i = 0; i < 30; i++) {
            simpleExecutor.execute(getRunnableWithDelay("#"+i));
            try {
                Thread.sleep(SYNCHRONIZE_DELAY);
                assertEquals(1, simpleExecutor.getLiveThreadsCount());
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /*
    Запуск параллельно n - 1 задач несколько раз (должно создаться n - 1 потоков) и задачи должны завершится
    примерно одновременно
    */
    @Test
    public void parallel() {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        final int taskAmount = 10;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < taskAmount; j++) {
                simpleExecutor.execute(getRunnableWithDelay("#" + j));
            }
            try {
                Thread.sleep(SYNCHRONIZE_DELAY);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        assertEquals(taskAmount, simpleExecutor.getLiveThreadsCount());
    }

    /*
    Запуск параллельно n + m задач несколько раз (должно создаться n потоков) и первые n задач должны завершится
    примерно одновременно, вторые m задач должны завершиться чуть позже первых n и тоже примерно одновременно
     */
    @Test
    public void anotherParallel() {
        SimpleExecutor simpleExecutor = new SimpleExecutor();
        final int firstTaskAmount = 20;
        final int secondTaskAmount = firstTaskAmount - 5;
        try {
            for (int i = 0; i < firstTaskAmount; i++) {
                simpleExecutor.execute(getRunnableWithDelay("#"+i));
            }
            System.out.println();
            for (int i = 0; i < secondTaskAmount; i++) {
                simpleExecutor.execute(getRunnableWithDelay("#"+i));
            }
            Thread.sleep(SYNCHRONIZE_DELAY);
            assertEquals(firstTaskAmount, simpleExecutor.getLiveThreadsCount());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
