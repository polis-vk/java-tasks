package ru.mail.polis.homework.concurrency.executor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleExecutorTest {

    private SimpleExecutor simpleExecutor;
    private final Runnable firstRunnable = () -> {
        System.out.println("1. Doing job: " + Thread.currentThread().getName());
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    };
    private final Runnable secondRunnable = () -> {
        System.out.println("2. Doing job: " + Thread.currentThread().getName());
        try {
            Thread.sleep(5);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    };


    @Before
    public void initExecutor() {
        simpleExecutor = new SimpleExecutor();
    }

    // Запуск 1 задачи несколько раз с интервалом (должен создаться только 1 поток)
    @Test
    public void threadsAmount() {
        Runnable runnable = () -> System.out.println("Doing job: " + Thread.currentThread().getName());
        for (int i = 0; i < 100; i++) {
            simpleExecutor.execute(runnable);
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        assertEquals(1, simpleExecutor.getLiveThreadsCount());
    }

    // запуск параллельно n - 1 задач несколько раз (должно создаться n - 1 потоков) и задачи должны завершится
    // примерно одновременно
    @Test
    public void parallel() {
        final int taskAmount = 20;
        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < taskAmount; j++) {
                simpleExecutor.execute(firstRunnable);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        assertEquals(taskAmount, simpleExecutor.getLiveThreadsCount());
    }

    // запуск параллельно n + m задач несколько раз (должно создаться n потоков) и первые n задач должны завершится
    // примерно одновременно, вторые m задач должны завершиться чуть позже первых n и тоже примерно одновременно
    @Test
    public void anotherParallel() {
        final int firstTaskAmount = 15;
        final int secondTaskAmount = 10;
        try {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < firstTaskAmount; j++) {
                    simpleExecutor.execute(firstRunnable);
                }
                Thread.sleep(50);
                for (int j = 0; j < secondTaskAmount; j++) {
                    simpleExecutor.execute(secondRunnable);
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        assertEquals(firstTaskAmount, simpleExecutor.getLiveThreadsCount());
    }

}