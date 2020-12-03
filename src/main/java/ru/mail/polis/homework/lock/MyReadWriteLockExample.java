package ru.mail.polis.homework.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class MyReadWriteLockExample {
    private static ReadWriteLock readWriteLock = new MyReadWriteLock(10);
    private static final int SLEEP_TIME = 1000;

    public static void main(String[] args) {
        new Thread(MyReadWriteLockExample::read).start();
        new Thread(MyReadWriteLockExample::read).start();
        new Thread(MyReadWriteLockExample::read).start();

        new Thread(MyReadWriteLockExample::write).start();
        new Thread(MyReadWriteLockExample::write).start();

        new Thread(MyReadWriteLockExample::read).start();
        new Thread(MyReadWriteLockExample::read).start();
        new Thread(MyReadWriteLockExample::read).start();

        try {
            Thread.sleep(SLEEP_TIME * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(MyReadWriteLockExample::write).start();
        new Thread(MyReadWriteLockExample::write).start();

    }

    private static void write() {
        Lock lock = readWriteLock.writeLock();
        try {
            lock.lock();
            System.out.println("Write started in thread " + Thread.currentThread().getName());
            Thread.sleep(SLEEP_TIME);
            System.out.println("Write completed in thread " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    private static void read() {
        Lock lock = readWriteLock.readLock();
        try {
            lock.lock();
            System.out.println("Read started in thread " + Thread.currentThread().getName());
            Thread.sleep(SLEEP_TIME);
            System.out.println("Read completed in thread " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
