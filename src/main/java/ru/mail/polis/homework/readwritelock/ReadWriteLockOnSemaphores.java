package ru.mail.polis.homework.readwritelock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class ReadWriteLockOnSemaphores implements ReadWriteLock {
    private static final int MAX_AMOUNT_OF_THREADS = 100;

    private final Semaphore semaphore = new Semaphore(MAX_AMOUNT_OF_THREADS, true);

    private final ReadLockOnSemaphores readLock = new ReadLockOnSemaphores();
    private final WriteLockOnSemaphores writeLock = new WriteLockOnSemaphores();

    private final AtomicInteger readAmount = new AtomicInteger(0);
    private final AtomicInteger writeAmount = new AtomicInteger(0);

    private class ReadLockOnSemaphores extends AbstractLock {
        @Override
        public void lock() {
            try {
                semaphore.acquire();
                readAmount.incrementAndGet();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            finally {
//                semaphore.release();
//            }
        }

        @Override
        public void unlock() {
            readAmount.decrementAndGet();
            semaphore.release();
        }
    }

    private class WriteLockOnSemaphores extends AbstractLock {
        @Override
        public void lock() {
            try {
                semaphore.acquire(MAX_AMOUNT_OF_THREADS);
                writeAmount.incrementAndGet();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            finally {
//                System.out.println("FINALLY RELEASED WRITE LOCK");
//                semaphore.release(MAX_AMOUNT_OF_THREADS);
//            }
        }

        @Override
        public void unlock() {
            writeAmount.decrementAndGet();
            semaphore.release(MAX_AMOUNT_OF_THREADS);
        }
    }

    @Override
    public Lock readLock() {
       return readLock;
    }

    @Override
    public Lock writeLock() {
       return writeLock;
    }
}
