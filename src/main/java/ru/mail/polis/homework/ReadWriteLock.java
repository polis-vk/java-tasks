package ru.mail.polis.homework;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ReadWriteLock {
    private final int size;
    private final Semaphore semaphore;
    private final ReadLock readLock = new ReadLock();
    private final WriteLock writeLock = new WriteLock();

    public ReadWriteLock(int size) {
        if (size <= 0)
            throw new IllegalArgumentException("Amount should be more than 1");
        this.size = size;
        this.semaphore = new Semaphore(size, true);
    }

    public ReadLock readLock() {
        return readLock;
    }

    public WriteLock writeLock() {
        return writeLock;
    }

    public int getSize() {
        return size;
    }

    class ReadLock {

        public void lock() throws InterruptedException {
            semaphore.acquire(1);
        }

        public void unlock() {
            semaphore.release(1);
        }

    }

    class WriteLock {

        public void lock() throws InterruptedException {
            semaphore.acquire(size);
        }

        public void unlock() {
            semaphore.release(size);
        }

    }

    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReadWriteLock(30);
        Worker worker = new Worker(readWriteLock);
        worker.start();
    }

}

class Worker {

    private final ReadWriteLock readWriteLock;
    private final List<Reader> threadList = new ArrayList<>();
    private final Writer writer = new Writer();

    public Worker(ReadWriteLock readWriteLock) {
        this.readWriteLock = readWriteLock;
        initThreads();
    }

    private void initThreads() {
        for (int i = 0; i < readWriteLock.getSize(); i++) {
            threadList.add(new Reader());
        }
    }

    public void start() {
        for (Thread thread : threadList) {
            thread.start();
        }
        writer.start();
    }

    class Writer extends Thread {
        @Override
        public void run() {
            for (; ; ) {
                try {
                    readWriteLock.writeLock().lock();
                    System.out.println(Thread.currentThread().getName() + " is writing");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupted");
                    break;
                } finally {
                    readWriteLock.writeLock().unlock();
                }
            }
        }
    }

    class Reader extends Thread {
        @Override
        public void run() {
            for (; ; ) {
                try {
                    readWriteLock.readLock().lock();
                    System.out.println(Thread.currentThread().getName() + " is reading");
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " interrupted");
                    break;
                } finally {
                    readWriteLock.readLock().unlock();
                }
            }
        }
    }
}

