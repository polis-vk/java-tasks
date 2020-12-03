package ru.mail.polis.homework.multicore;

import java.util.concurrent.Semaphore;

public class ReadWriteLockR {
    private final int size;
    private final Semaphore semaphore;
    private final MyReadLock readLock = new MyReadLock();
    private final MyWriteLock writeLock = new MyWriteLock();

    ReadWriteLockR(int size) {
        if (size < 1) {
            throw new IllegalArgumentException();
        }
        this.size = size;
        semaphore = new Semaphore(size);
    }

    ReadWriteLockR() {
        size = 100;
        semaphore = new Semaphore(size);
    }

    private class MyReadLock {
        public void lock() throws InterruptedException {
            semaphore.acquire();
        }

        public void unlock() {
            semaphore.release();
        }
    }

    private class MyWriteLock {
        public void lock() throws InterruptedException {
            semaphore.acquire(size);
        }

        public void unlock() {
            semaphore.release(size);
        }
    }

    public MyReadLock readLock() {
        return readLock;
    }

    public MyWriteLock writeLock() {
        return writeLock;
    }
}
