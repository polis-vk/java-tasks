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
    public void manyRunnableTest() throws InterruptedException {
        CountDownLatch first = new CountDownLatch(1);
        CountDownLatch second = new CountDownLatch(1);
        CountDownLatch third = new CountDownLatch(1);
        AtomicInteger counter = new AtomicInteger();
        AtomicInteger error = new AtomicInteger();
        executor.execute(() -> {
            first.countDown();
            counter.incrementAndGet();
        });
        executor.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                error.incrementAndGet();
            }
            second.countDown();
            counter.incrementAndGet();
        });
        executor.execute(() -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                error.incrementAndGet();
            }
            third.countDown();
            counter.incrementAndGet();
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
        AtomicInteger counter = new AtomicInteger();
        AtomicInteger error = new AtomicInteger();

        executor.execute(() -> {
            first.countDown();
            counter.incrementAndGet();
            try {
                executeSecond.await(2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                error.incrementAndGet();
            }
            executor.shutdown();
        });

        executor.execute(() -> {
            second.countDown();
            counter.incrementAndGet();
        });
        executeSecond.countDown();

        assertTrue(first.await(5, TimeUnit.SECONDS));
        assertEquals(1, counter.get());

        try {
            executor.execute(counter::incrementAndGet);
            fail();
        } catch (java.util.concurrent.RejectedExecutionException e) {

        } catch (Throwable t) {
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
            first.countDown();
            counter.incrementAndGet();
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
            second.countDown();
            counter.incrementAndGet();
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