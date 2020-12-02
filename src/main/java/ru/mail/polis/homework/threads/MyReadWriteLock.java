package ru.mail.polis.homework.threads;

import java.util.concurrent.Semaphore;

public class MyReadWriteLock {

    private int size;
    private Semaphore semaphore;
    private MyReadLock readLock = new MyReadLock();
    private MyWriteLock writeLock = new MyWriteLock();


    public MyReadWriteLock(int size) {
        if (size <= 1)
            throw new IllegalArgumentException("");
        this.size = size;
        semaphore = new Semaphore(size, true);
    }

    public MyReadWriteLock() {
        this.size = 10;
        semaphore = new Semaphore(this.size, true);
    }

    public MyReadLock readLock() {
        return readLock;
    }

    public MyWriteLock writeLock() {
        return writeLock;
    }

    protected class MyReadLock {
        public void lock() throws InterruptedException {
            semaphore.acquire(1);
        }

        public void unlock() {
            semaphore.release(1);
        }
    }

    protected class MyWriteLock {
        public void lock() throws InterruptedException {
            semaphore.acquire(size);
        }

        public void unlock() {
            semaphore.release(size);
        }
    }
}
