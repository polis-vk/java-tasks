package ru.mail.polis.homework.concurrent.state;


import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.concurrency.executor.SimpleExecutor;

import java.util.concurrent.CountDownLatch;

public class SimpleExecutorTest {

    private static final int COUNT_OF_THREADS = 8;

    private static class ExampleTestTask implements Runnable {
        private final CountDownLatch latch;

        ExampleTestTask(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            System.out.println("Start");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }
            System.out.println("End");
            latch.countDown();
        }
    }

    @Test
    public void TestOneTask(){
        SimpleExecutor simpleExecutor = new SimpleExecutor(COUNT_OF_THREADS);
        CountDownLatch latch = new CountDownLatch(1);
        Assert.assertEquals(0, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.execute(new ExampleTestTask(latch));
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        Assert.assertEquals(1, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.shutDown();
    }

    @Test
    public void TestLimitCountOfTask() {
        SimpleExecutor simpleExecutor = new SimpleExecutor(COUNT_OF_THREADS);

        Assert.assertEquals(0, simpleExecutor.getLiveThreadsCount());
        CountDownLatch latch = new CountDownLatch(COUNT_OF_THREADS - 1);
        for (int i = 0; i < (COUNT_OF_THREADS - 1); ++i) {
            simpleExecutor.execute(new ExampleTestTask(latch));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        Assert.assertEquals(COUNT_OF_THREADS - 1, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.shutDown();
    }

    @Test
    public void TestUnLimitCountOfTask() {
        SimpleExecutor simpleExecutor = new SimpleExecutor(COUNT_OF_THREADS);

        Assert.assertEquals(0, simpleExecutor.getLiveThreadsCount());
        CountDownLatch latch = new CountDownLatch(COUNT_OF_THREADS + COUNT_OF_THREADS);
        for (int i = 0; i < (COUNT_OF_THREADS * COUNT_OF_THREADS); ++i) {
            simpleExecutor.execute(new ExampleTestTask(latch));
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        Assert.assertEquals(COUNT_OF_THREADS, simpleExecutor.getLiveThreadsCount());
        simpleExecutor.shutDown();
    }
}
