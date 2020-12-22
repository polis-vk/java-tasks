package ru.mail.polis.homework.executor;

import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.concurrency.executor.SimpleExecutor;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

public class SimpleExecutorTest {

    static class Task implements Runnable {

        private final CountDownLatch latch;
        private final int time;

        Task(CountDownLatch latch, int time) {
            if (time < 1) {
                throw new IllegalArgumentException();
            }
            this.latch = latch;
            this.time = time;
        }

        @Override
        public void run() {
            System.out.print("Starting");
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.print("Complete");
            latch.countDown();
        }
    }

    @Test
    public void Test1() throws InterruptedException {
        SimpleExecutor simpleExecutor = new SimpleExecutor(4);
        Random random = new Random();
        int min = 100;
        int max = 500;

        for (int i = 0; i < 10; i++) {
            int time = random.nextInt((max - min) + 1) + min;
            CountDownLatch latch = new CountDownLatch(1);

            simpleExecutor.execute(new Task(latch, time));
            Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());

            latch.await();
            Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());
        }
        simpleExecutor.off();
    }

    @Test
    public void Task2() throws InterruptedException {
        Random random = new Random();
        int minThreads = 10;
        int maxThreads = 20;

        final int N = random.nextInt((maxThreads - minThreads) + 1) + minThreads;
        SimpleExecutor simpleExecutor = new SimpleExecutor(N);

        int min = 100;
        int max = 500;

        for (int i = 0; i < 10; i++) {
            int time = random.nextInt((max - min) + 1) + min;
            CountDownLatch latch = new CountDownLatch(N - 1);

            for (int j = 0; j < N - 1; j++) {
                simpleExecutor.execute(new Task(latch, time));
            }
            Assert.assertEquals(N - 1, simpleExecutor.getLiveThreadsCount());

            latch.await();
            Assert.assertEquals(N - 1, simpleExecutor.getLiveThreadsCount());
        }
        simpleExecutor.off();
    }

    @Test
    public void Task3() throws InterruptedException {
        Random random = new Random();
        int minThreads = 10;
        int maxThreads = 30;

        final int N = random.nextInt((maxThreads - minThreads) + 1) + minThreads;
        final int M = random.nextInt((maxThreads - minThreads) + 1) + minThreads;
        SimpleExecutor simpleExecutor = new SimpleExecutor(N);

        int min = 100;
        int max = 300;

        for (int i = 0; i < 10; i++) {
            int time = random.nextInt((max - min) + 1) + min;
            CountDownLatch latch = new CountDownLatch(N + M);

            for (int j = 0; j < N + M; j++) {
                simpleExecutor.execute(new Task(latch, time));
            }
            Assert.assertEquals(N, simpleExecutor.getLiveThreadsCount());

            latch.await();
            Assert.assertEquals(N, simpleExecutor.getLiveThreadsCount());
        }
        simpleExecutor.off();
    }
}