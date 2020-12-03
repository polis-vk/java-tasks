package ru.mail.polis.homework.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class MyReadWriteLock implements ReadWriteLock {
    private MyReadLock readLock = new MyReadLock();
    private MyWriteLock writeLock = new MyWriteLock();
    private Semaphore semaphore;
    private final int size;

    private AtomicInteger readers = new AtomicInteger(0);
    private AtomicInteger writers = new AtomicInteger(0);

    MyReadWriteLock(int size) {
        this.size = size;
        semaphore = new Semaphore(size);
    }

    private class MyReadLock implements Lock {
        @Override
        public void lock() {
            try {
                synchronized (MyReadWriteLock.this) {
                    while (writers.get() > 0) {
                        MyReadWriteLock.this.wait();
                    }
                }
                semaphore.acquire();
                readers.incrementAndGet();
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
            return false;
        }

        @Override
        public void unlock() {
            semaphore.release();
        }

        @Override
        public Condition newCondition() {
            return null;
        }
    }

    private class MyWriteLock implements Lock {
        @Override
        public void lock() {
            try {
                writers.incrementAndGet();
                semaphore.acquire(size);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        public boolean tryLock(long l, TimeUnit timeUnit) throws InterruptedException {
            return false;
        }

        @Override
        public void unlock() {
            if (writers.decrementAndGet() == 0) {
                synchronized (MyReadWriteLock.this) {
                    MyReadWriteLock.this.notifyAll();
                }
            }
            semaphore.release(size);
        }

        @Override
        public Condition newCondition() {
            return null;
        }
    }

    public MyReadLock readLock() {
        return readLock;
    }

    public MyWriteLock writeLock() {
        return writeLock;
    }
}
