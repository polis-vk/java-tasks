package ru.mail.polis.homework.concurrency.executor;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class SingleExecutorTest {

    private SingleExecutor executor;

    @Before
    public void setUp() {
        executor = new SingleExecutor();
    }

    @After
    public void tearDown() {
        executor.shutdown();
    }

    @Test
    public void simpleTest() throws InterruptedException {
        CountDownLatch count = new CountDownLatch(1);
        executor.execute(count::countDown);
        assertTrue(count.await(5, TimeUnit.SECONDS));
    }

    @Test
    public void simple_second_Test() throws InterruptedException {
        CountDownLatch count = new CountDownLatch(1);
        executor.execute(count::countDown);
        assertTrue(count.await(5, TimeUnit.SECONDS));
        Thread.sleep(10000);
        count = new CountDownLatch(1);
        executor.execute(count::countDown);
        assertTrue(count.await(5, TimeUnit.SECONDS));
    }


    @Test
    public void manyRunnableTest() throws InterruptedException {
        CountDownLatch first = new CountDownLatch(1);
        CountDownLatch second = new CountDownLatch(1);
        CountDownLatch third = new CountDownLatch(1);
        AtomicInteger counter = new AtomicInteger();
        AtomicInteger error = new AtomicInteger();
        executor.execute(() -> {
            counter.incrementAndGet();
            first.countDown();
        });
        executor.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                error.incrementAndGet();
            }
            counter.incrementAndGet();
            second.countDown();
        });
        executor.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                error.incrementAndGet();
            }
            counter.incrementAndGet();
            third.countDown();
        });
        assertTrue(first.await(5, TimeUnit.SECONDS));
        assertEquals(1, counter.get());

        assertTrue(second.await(5, TimeUnit.SECONDS));
        assertEquals(2, counter.get());

        assertTrue(third.await(5, TimeUnit.SECONDS));
        assertEquals(3, counter.get());
        assertEquals(0, error.get());
    }

    @Test
    public void shutDownTest() throws InterruptedException {
        CountDownLatch first = new CountDownLatch(1);
        CountDownLatch second = new CountDownLatch(1);
        CountDownLatch executeSecond = new CountDownLatch(1);
        CountDownLatch countDownLatch110 = new CountDownLatch(1);
        CountDownLatch afterShutdown = new CountDownLatch(1);
        AtomicInteger counter = new AtomicInteger();
        AtomicInteger error = new AtomicInteger();

        executor.execute(() -> {
            counter.incrementAndGet();
            first.countDown();
            try {
                executeSecond.await(2, TimeUnit.SECONDS);
                executor.shutdown();
            } catch (InterruptedException e) {
                error.incrementAndGet();
            }
            afterShutdown.countDown();
        });

        executor.execute(() -> {
            try {
                countDownLatch110.await(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                error.incrementAndGet();
            }
            counter.incrementAndGet();
            second.countDown();
        });
        executeSecond.countDown();

        assertTrue(first.await(5, TimeUnit.SECONDS));
        assertEquals(1, counter.get());
        countDownLatch110.countDown();

        afterShutdown.await(5, TimeUnit.SECONDS);
        try {
            executor.execute(counter::incrementAndGet);
            fail();
        } catch (java.util.concurrent.RejectedExecutionException e) {

        } catch (Throwable t) {
            t.printStackTrace();
            fail(t.getClass().getName());
        }

        assertTrue(second.await(5, TimeUnit.SECONDS));
        assertEquals(2, counter.get());
        assertEquals(0, error.get());
    }

    @Test
    public void shutdownNowTest() throws InterruptedException {
        CountDownLatch first = new CountDownLatch(1);
        CountDownLatch second = new CountDownLatch(1);
        CountDownLatch executeSecond = new CountDownLatch(1);
        AtomicInteger counter = new AtomicInteger();
        AtomicInteger error = new AtomicInteger();
        executor.execute(() -> {
            counter.incrementAndGet();
            first.countDown();
        });
        executor.execute(() -> {
            try {
                executeSecond.await(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                return;
            }

            executor.shutdownNow();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                error.incrementAndGet();
            }
            counter.incrementAndGet();
            second.countDown();
        });
        assertTrue(first.await(5, TimeUnit.SECONDS));
        assertEquals(1, counter.get());

        assertTrue(second.await(5, TimeUnit.SECONDS));
        assertEquals(2, counter.get());
        try {
            executor.execute(counter::incrementAndGet);
            fail();
        } catch (java.util.concurrent.RejectedExecutionException e) {

        } catch (Throwable t) {
            fail(t.getClass().getName());
        }
        assertEquals(1, error.get());
    }

}