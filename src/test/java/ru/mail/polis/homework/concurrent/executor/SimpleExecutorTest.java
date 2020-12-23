package ru.mail.polis.homework.concurrent.executor;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import ru.mail.polis.homework.concurrency.executor.SimpleExecutor;

public class SimpleExecutorTest {

    @Test
    public void oneTaskWithInterval() {
        SimpleExecutor executor = new SimpleExecutor(10);
        for (int i = 0; i < 10; i++) {
            executor.execute(() -> {
                System.out.println("Some work results");
            });

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            assertEquals(executor.getLiveThreadsCount(), 1);
        }
    }

    @Test
    public void runParallelTasks() throws InterruptedException {
        int N = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(N);
        SimpleExecutor executor = new SimpleExecutor(N);
        for (int j = 0; j < 4; j++) {
            System.out.println(j);
            CountDownLatch countDownLatch = new CountDownLatch(N);
            for (int i = 0; i < N; i++) {
                executorService.execute(() -> {
                    countDownLatch.countDown();
                    try {
                        countDownLatch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    executor.execute(() -> {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("Some work results");
                    });
                });
            }
            executorService.awaitTermination(5, TimeUnit.SECONDS);
            assertEquals(executor.getLiveThreadsCount(), N);
        }
    }
}