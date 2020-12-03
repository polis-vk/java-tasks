package ru.mail.polis.homework.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class MyReadWriteLock implements ReadWriteLock {
    private MyReadLock readLock = new MyReadLock();
    private MyWriteLock writeLock = new MyWriteLock();
    private Semaphore semaphore;
    private final int size;

    MyReadWriteLock(int size) {
        this.size = size;
        semaphore = new Semaphore(size);
    }

    private class MyReadLock implements Lock {
        @Override
        public void lock() {
            try {
                semaphore.acquire();
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
