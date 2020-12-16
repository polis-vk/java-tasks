package ru.mail.polis.homework.concurrency.executor;

import org.testng.annotations.Test;

import static org.junit.Assert.*;

public class SimpleExecutorTest {

    private Runnable getRunnableWithDelay(String jobName) {
        return () -> {
            System.out.println("Doing job " + jobName +": " + Thread.currentThread().getName());
            try {
                Thread.sleep(100);
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
                Thread.sleep(200);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            assertEquals(1, simpleExecutor.getLiveThreadsCount());
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
        for (int j = 0; j < taskAmount; j++) {
            simpleExecutor.execute(getRunnableWithDelay("#"+j));
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
        final int firstTaskAmount = 30;
        final int secondTaskAmount = firstTaskAmount - 5;
        try {
            for (int i = 0; i < firstTaskAmount; i++) {
                simpleExecutor.execute(getRunnableWithDelay("#"+i));
            }
            Thread.sleep(150);
            System.out.println();
            for (int i = 0; i < secondTaskAmount; i++) {
                simpleExecutor.execute(getRunnableWithDelay("#"+i));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertEquals(firstTaskAmount, simpleExecutor.getLiveThreadsCount());
    }
}
