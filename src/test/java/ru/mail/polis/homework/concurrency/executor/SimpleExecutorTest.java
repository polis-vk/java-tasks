package ru.mail.polis.homework.concurrency.executor;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleExecutorTest {

    private SimpleExecutor simpleExecutor;


    @Before
    public void initExecutor() {
        simpleExecutor = new SimpleExecutor();
    }

    // Запуск 1 задачи несколько раз с интервалом (должен создаться только 1 поток)
    @Test
    public void threadsAmount() throws InterruptedException {
        Runnable runnable = () -> {
            System.out.println("Doing job: " + Thread.currentThread().getName());
        };
        for (int i = 0; i < 10; i++) {
            simpleExecutor.execute(runnable);
            Thread.sleep(2500);
        }
        assertEquals(1, simpleExecutor.getLiveThreadsCount());
    }

    @Test
    public void parallel() {
        final int taskAmount = 20;
        Runnable runnable = () -> {
            System.out.println("Doing job: " + Thread.currentThread().getName());
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < taskAmount; j++) {
                simpleExecutor.execute(runnable);
            }
            simpleExecutor.waitAll();
            simpleExecutor.getThreadPool().stream().forEach(e -> System.out.println(e.getState()));
        }
        assertEquals(taskAmount, simpleExecutor.getLiveThreadsCount());
    }

    @Test
    public void anotherParallel() {
        final int firstTaskAmount = 15;
        final int secondTaskAmount = 10;
        Runnable firstRunnable = () -> {
            System.out.println("1. Doing job: " + Thread.currentThread().getName());
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        Runnable secondRunnable = () -> {
            System.out.println("2. Doing job: " + Thread.currentThread().getName());
            try {
                Thread.sleep(150);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < firstTaskAmount; j++) {
                simpleExecutor.execute(firstRunnable);
            }
            simpleExecutor.waitAll();
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < secondTaskAmount; j++) {
                simpleExecutor.execute(secondRunnable);
            }
            simpleExecutor.waitAll();
        }
        assertEquals(firstTaskAmount, simpleExecutor.getLiveThreadsCount());
    }

}