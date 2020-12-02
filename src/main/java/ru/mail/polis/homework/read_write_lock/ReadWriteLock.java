package ru.mail.polis.homework.read_write_lock;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class ReadWriteLock {
    private final AtomicInteger readerCount;
    private final Semaphore semaphore;

    public ReadWriteLock() {
        readerCount = new AtomicInteger(0);
        semaphore = new Semaphore(1);
    }

    public void acquireReadLock() {
        readerCount.incrementAndGet();
        if (readerCount.get() == 1) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void releaseReadLock() {
        readerCount.decrementAndGet();
        if (readerCount.get() == 0) {
            semaphore.release();
        }
    }

    public void acquireWriteLock() {
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void releaseWriteLock() {
        semaphore.release();
    }
}
