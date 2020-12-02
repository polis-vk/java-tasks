package ru.mail.polis.homework.threads;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MyReadWriteLockTest {

    MyReadWriteLock readWriteLock = new MyReadWriteLock();
    Logger logger = Logger.getLogger(MyReadWriteLockTest.class.getName());

    private class WriteThread extends Thread {
        @Override
        public void run() {
            for (;;) {
                try {
                    readWriteLock.writeLock().lock();
                    logger.log(Level.INFO, Thread.currentThread().getName() + " writing");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, Thread.currentThread().getName() + " interrupted");
                    e.printStackTrace();
                } finally {
                    readWriteLock.writeLock().unlock();
                }
            }
        }
    }

    private class ReadThread extends Thread {
        @Override
        public void run() {
            for (;;) {
                try {
                    readWriteLock.readLock().lock();
                    logger.log(Level.INFO, Thread.currentThread().getName() + " reading");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    logger.log(Level.WARNING, Thread.currentThread().getName() + " interrupted");
                    e.printStackTrace();
                } finally {
                    readWriteLock.readLock().unlock();
                }
            }
        }
    }

    @Test
    public void MyReadWriteLockTest() throws InterruptedException {
        List<Thread> readThreads = new ArrayList<>();
        WriteThread writeThread1 = new WriteThread();
        WriteThread writeThread2 = new WriteThread();
        writeThread1.start();
        for (int i = 0; i < 10; i++) {
            ReadThread readThread = new ReadThread();
            readThreads.add(readThread);
            readThread.start();
        }
        writeThread2.start();
        Thread.sleep(100000);
    }
}
