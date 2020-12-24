package ru.mail.polis.homework.concurrency.executor;

import org.junit.Test;
import java.util.concurrent.CountDownLatch;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class SimpleExecutorTest {
    private static class Task implements Runnable {
        final CountDownLatch latch;
        final int sleepTimeMillis;

        Task(CountDownLatch lock, int sleepTimeMillis) {
            this.latch = lock;
            this.sleepTimeMillis = sleepTimeMillis;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(sleepTimeMillis);
                System.out.printf("Task %s finished.\n", this);
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                System.err.println("Failed to run the task.");
                return;
            }
            latch.countDown();
        }
    }

    @Test
    public void singleTaskMultipleTimesTest() {
        SimpleExecutor executor = new SimpleExecutor(10);
        for (int i = 0; i < 25; i++) {
            CountDownLatch latch = new CountDownLatch(1);
            executor.execute(new Task(latch, 50));
            try {
                latch.await();
            } catch (InterruptedException ex) {
                fail();
            }
            assertEquals(1, executor.getLiveWorkersCount());
        }
    }

    @Test
    public void maxTasksInParallelTest() {
        final int maxTasksCount = 25;
        SimpleExecutor executor = new SimpleExecutor(maxTasksCount);
        CountDownLatch latch = new CountDownLatch(maxTasksCount);
        for (int i = 0; i < maxTasksCount; i++) {
            executor.execute(new Task(latch, 5000));
        }
        try {
            latch.await();
        } catch (InterruptedException ex) {
            fail();
        }
        assertEquals(maxTasksCount, executor.getLiveWorkersCount());
    }

    @Test
    public void moreThanMaxTasksInParallelTest() {
        final int maxTasksCount = 25;
        final int tasksCount = maxTasksCount * 3 / 2;
        SimpleExecutor executor = new SimpleExecutor(maxTasksCount);
        CountDownLatch latch = new CountDownLatch(tasksCount);
        for (int i = 0; i < tasksCount; i++) {
            executor.execute(new Task(latch, 5000));
        }
        try {
            latch.await();
        } catch (InterruptedException ex) {
            fail();
        }
        assertEquals(maxTasksCount, executor.getLiveWorkersCount());
    }
}
