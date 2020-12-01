package ru.mail.polis.homework;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ReadWriteLockBySemaphore {
    private static int SEMAPHORE_SIZE = 10;
    private final Semaphore semaphore;
    private AtomicInteger readerAmount;
    private AtomicInteger writerAmount;

    public ReadWriteLockBySemaphore() {
        this.readerAmount = new AtomicInteger(0);
        this.writerAmount = new AtomicInteger(0);
        this.semaphore = new Semaphore(SEMAPHORE_SIZE);
    }

    public void startReading() throws InterruptedException {
        while (writerAmount.get() != 0) {

        }

        readerAmount.incrementAndGet();
    }

    public void stopReading() {
        readerAmount.decrementAndGet();
        semaphore.release();
    }

    public void startWrite() throws InterruptedException {
        writerAmount.incrementAndGet();

        while (this.readerAmount.get() != 0) {

        }

        semaphore.acquire(SEMAPHORE_SIZE);
    }

    public void stopWrite() {
        writerAmount.decrementAndGet();
        semaphore.release(SEMAPHORE_SIZE);
    }

    public static void main(String[] args) {
        ReadWriteLockBySemaphore semaphore = new ReadWriteLockBySemaphore();

        ExecutorService service = Executors.newFixedThreadPool(SEMAPHORE_SIZE);

        for (int i = 0; i < SEMAPHORE_SIZE; i++) {
            int finalI = i;

            service.submit(() -> {
                if (finalI % 2 == 0) {
                    try {
                        semaphore.startReading();
                        System.out.println("READING");
                        for (int j = 0; j < 1_000_000; j++) {
                            Math.signum(Math.random() * 1000 * 10 - 12 * 100 * Math.random());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.stopReading();
                        System.out.println("READ");
                    }
                } else {
                    try {
                        semaphore.startWrite();
                        System.out.println("WRITING");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        semaphore.stopWrite();
                        System.out.println("WRITTEN");
                    }
                }
            });
        }

        service.shutdown();
    }
}

