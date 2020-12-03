package ru.mail.polis.homework.readwritelock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ReadWriteLock {
    private final Read read = new Read();
    private final Write write = new Write();
    private final Semaphore semaphore;
    private final int size;

    public ReadWriteLock(int size) {
        if (size <= 1) throw new IllegalArgumentException("Size should be more than 1");
        this.size = size;
        this.semaphore = new Semaphore(size, true);
    }

    public int getSize() {
        return size;
    }

    public Read readLock() {
        return read;
    }

    public Write writeLock() {
        return write;
    }

    class Read {
        public void lock() throws InterruptedException {
            semaphore.acquire();
        }

        public void unlock() {
            semaphore.release();
        }
    }

    class Write {
        public void lock() throws InterruptedException {
            semaphore.acquire(size);
        }

        public void unlock() {
            semaphore.release(size);
        }
    }

    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReadWriteLock(2);
        Test test = new Test(readWriteLock);
        test.start();
    }
}

class Test {
    private final ReadWriteLock readWriteLock;
    private final List<Reader> threadList = new ArrayList<>();
    private final Writer writer = new Writer();

    public Test(ReadWriteLock readWriteLock) {
        this.readWriteLock = readWriteLock;
        initThreads();
    }

    private void initThreads() {
        for (int i = 0; i < readWriteLock.getSize() - 1; i++) {
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
            while (true) {
                try {
                    readWriteLock.writeLock().lock();
                    System.out.println(Thread.currentThread().getName() + " is writing");
                    Thread.sleep(1000);
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
            while (true) {
                try {
                    readWriteLock.readLock().lock();
                    System.out.println(Thread.currentThread().getName() + " is reading");
                    Thread.sleep(1000);
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
