package ru.mail.polis.homework.concurrency;

import java.util.concurrent.Semaphore;

public class ReadWriteWithSemaphore {
    private static final int MAX_READERS_AMOUNT = 1000;

    private final Semaphore semaphore;
    private final int readersAmount;
    private final ReadLockWithSemaphore readLock = new ReadLockWithSemaphore();
    private final WriteLockWithSemaphore writeLock = new WriteLockWithSemaphore();

    public ReadWriteWithSemaphore() {
        this.readersAmount = MAX_READERS_AMOUNT;
        this.semaphore = new Semaphore(MAX_READERS_AMOUNT, true);
    }

    public ReadWriteWithSemaphore(int readersAmount) {
        if (readersAmount < 1) throw new IllegalArgumentException("readersAmount can't be less than 1");
        this.readersAmount = readersAmount;
        this.semaphore = new Semaphore(readersAmount, true);
    }

    public ReadLockWithSemaphore getReadLock() {
        return readLock;
    }

    public WriteLockWithSemaphore getWriteLock() {
        return writeLock;
    }

    class ReadLockWithSemaphore {
        public void lock() throws InterruptedException {
            semaphore.acquire();
        }

        public void unlock() {
            semaphore.release();
        }
    }

    class WriteLockWithSemaphore {
        public void lock() throws InterruptedException {
            semaphore.acquire(readersAmount);
        }

        public void unlock() {
            semaphore.release(readersAmount);
        }
    }
}
