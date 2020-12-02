package ru.mail.polis.homework;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class ReadWriteLockBySemaphore implements ReadWriteLock {
    private static int SEMAPHORE_SIZE = 10;
    private final Semaphore semaphore;
    private AtomicInteger readerAmount;
    private Lock readLock;
    private Lock writeLock;
    private AtomicInteger writerAmount;

    private class WriteLock implements Lock {
        @Override
        public void lock() {
            writerAmount.incrementAndGet();

            try {
                semaphore.acquire(SEMAPHORE_SIZE);
            } catch (InterruptedException e) {
                semaphore.release(SEMAPHORE_SIZE);
            }
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {

        }

        @Override
        public boolean tryLock() {
            return false;
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public void unlock() {
            writerAmount.decrementAndGet();
            semaphore.release(SEMAPHORE_SIZE);
        }

        @Override
        public Condition newCondition() {
            return null;
        }
    }

    private class ReadLock implements Lock {
        @Override
        public void lock() {
            readerAmount.incrementAndGet();

            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                semaphore.release();
            }
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {

        }

        @Override
        public boolean tryLock() {
            return false;
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public void unlock() {
            readerAmount.decrementAndGet();
            semaphore.release();
        }

        @Override
        public Condition newCondition() {
            return null;
        }
    }

    public ReadWriteLockBySemaphore() {
        this.readLock = new ReadLock();
        this.writeLock = new WriteLock();
        this.readerAmount = new AtomicInteger(0);
        this.writerAmount = new AtomicInteger(0);
        this.semaphore = new Semaphore(SEMAPHORE_SIZE);
    }

    @Override
    public Lock readLock() {
        return this.readLock;
    }

    @Override
    public Lock writeLock() {
        return this.writeLock;
    }


    public static void main(String[] args) {
        ReadWriteLockBySemaphore readWriteLock = new ReadWriteLockBySemaphore();

        ExecutorService service = Executors.newFixedThreadPool(SEMAPHORE_SIZE);

        for (int i = 0; i < SEMAPHORE_SIZE; i++) {
            int finalI = i;

            service.submit(() -> {

                Lock readLock = readWriteLock.readLock();
                Lock writeLock = readWriteLock.writeLock();

                if (finalI % 2 == 0) {
                    try {
                        readLock.lock();
                        System.out.println("READING");
                        for (int j = 0; j < 1_000_000; j++) {
                            Math.signum(Math.random() * 1000 * 10 - 12 * 100 * Math.random());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        readLock.unlock();
                        System.out.println("READ");
                    }
                } else {
                    try {
                        writeLock.lock();
                        System.out.println("WRITING");
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        writeLock.unlock();
                        System.out.println("WRITTEN");
                    }
                }
            });
        }

        service.shutdown();
    }
}

