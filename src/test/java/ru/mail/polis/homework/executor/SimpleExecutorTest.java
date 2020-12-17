package ru.mail.polis.homework.executor;

import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.concurrency.executor.SimpleExecutor;

public class SimpleExecutorTest {

    @Test
    public void Test1() {
        SimpleExecutor simpleExecutor = new SimpleExecutor(4);

        Runnable task = () -> {
            System.out.print("Starting");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("Complete");
        };

        Assert.assertEquals(0, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.execute(task);
        Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.execute(task);
        Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());

        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.off();
    }

    @Test
    public void Task2() {

        final int N = 10;
        SimpleExecutor simpleExecutor = new SimpleExecutor(N);

        //450ms
        Runnable task = () -> {
            try {
                Thread.sleep(450);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Assert.assertEquals(0, simpleExecutor.getLiveThreadsCount());

        for (int i = 0; i < N; i++) {
            simpleExecutor.execute(task);
        }

        Assert.assertEquals(N, simpleExecutor.getLiveThreadsCount());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(N, simpleExecutor.getLiveThreadsCount());

        for (int i = 0; i < N; i++) {
            simpleExecutor.execute(task);
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(N, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.off();
    }

    @Test
    public void Task3() {
        final int N = 30;
        final int M = 15;

        SimpleExecutor simpleExecutor = new SimpleExecutor(N);

        //100ms
        Runnable task = () -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        Assert.assertEquals(0, simpleExecutor.getLiveThreadsCount());

        for (int i = 0; i < N + M; i++) {
            simpleExecutor.execute(task);
        }
        Assert.assertEquals(N, simpleExecutor.getLiveThreadsCount());
        try {
            Thread.sleep(6500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(N, simpleExecutor.getLiveThreadsCount());

        for (int i = 0; i < N + M; i++) {
            simpleExecutor.execute(task);
        }
        Assert.assertEquals(N, simpleExecutor.getLiveThreadsCount());
        try {
            Thread.sleep(6500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Assert.assertEquals(N, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.off();
    }
}