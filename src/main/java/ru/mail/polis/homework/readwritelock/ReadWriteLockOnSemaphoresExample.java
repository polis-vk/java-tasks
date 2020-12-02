package ru.mail.polis.homework.readwritelock;

import java.util.concurrent.locks.Lock;

public class ReadWriteLockOnSemaphoresExample {
    private final ReadWriteLockOnSemaphores readWriteLock = new ReadWriteLockOnSemaphores();

    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_BLUE = "\u001B[34m";

    private static final int TIME_SLEEP = 4000;

    public void somethingGet() {
        Lock lock = readWriteLock.readLock();
        lock.lock();
        try {
            System.out.println("Start " + ANSI_GREEN + "READING at " + ANSI_RESET + Thread.currentThread().getName());
            Thread.sleep(TIME_SLEEP);
            System.out.println("Finish " + ANSI_GREEN + "READING at " + ANSI_RESET + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void somethingSet() {
        Lock lock = readWriteLock.writeLock();
        lock.lock();
        try {
            System.out.println("Start " + ANSI_BLUE + "WRITING at " + ANSI_RESET + Thread.currentThread().getName());
            Thread.sleep(TIME_SLEEP);
            System.out.println("Finish " + ANSI_BLUE + "WRITING at " + ANSI_RESET + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ReadWriteLockOnSemaphoresExample example = new ReadWriteLockOnSemaphoresExample();

        new Thread(example::somethingGet).start();
        new Thread(example::somethingGet).start();
        new Thread(example::somethingGet).start();
        new Thread(example::somethingGet).start();

        new Thread(example::somethingSet).start();
        new Thread(example::somethingSet).start();

        new Thread(example::somethingGet).start();
        new Thread(example::somethingGet).start();

        new Thread(example::somethingSet).start();
    }
}
