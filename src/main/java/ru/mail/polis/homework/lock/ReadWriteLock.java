package ru.mail.polis.homework.lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ReadWriteLock implements java.util.concurrent.locks.ReadWriteLock {
    private final Semaphore semaphore;
    private final int n;
    private final MyLock readL = new MyLock(false);
    private final MyLock writeL = new MyLock(true);

    public ReadWriteLock(int n) {
        this.n = n;
        this.semaphore = new Semaphore(n);
    }

    @Override
    public Lock readLock() {
        return readL;
    }

    @Override
    public Lock writeLock() {
        return writeL;
    }


    class MyLock implements Lock {

        private final boolean isWriteLock;

        MyLock(boolean isWriteLock) {
            this.isWriteLock = isWriteLock;
        }

        @Override
        public void lock() {
            try {
                if (isWriteLock)
                    semaphore.acquire(n);
                else
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
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            return false;
        }

        @Override
        public void unlock() {
            if (isWriteLock)
                semaphore.release(n);
            else
                semaphore.release();

        }

        @Override
        public Condition newCondition() {
            return null;
        }
    }
}
