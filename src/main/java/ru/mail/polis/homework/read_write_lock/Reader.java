package ru.mail.polis.homework.read_write_lock;

public class Reader implements Runnable {
    private final ReadWriteLock readWriteLock;
    private final int readerNumber;

    public Reader(int readerNumber, ReadWriteLock readWriteLock) {
        this.readerNumber = readerNumber;
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void run() {
        System.out.println("Reader " + readerNumber + " started");
        readWriteLock.acquireReadLock();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        readWriteLock.releaseReadLock();
        System.out.println("Reader " + readerNumber + " finished");
    }
}
