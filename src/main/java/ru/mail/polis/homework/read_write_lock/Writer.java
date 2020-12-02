package ru.mail.polis.homework.read_write_lock;

public class Writer implements Runnable {
    private final ReadWriteLock readWriteLock;
    private final int writerNumber;

    public Writer(int writerNumber, ReadWriteLock readWriteLock) {
        this.writerNumber = writerNumber;
        this.readWriteLock = readWriteLock;
    }

    @Override
    public void run() {
        System.out.println("Writer " + writerNumber + " started");
        readWriteLock.acquireWriteLock();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        readWriteLock.releaseWriteLock();
        System.out.println("Writer " + writerNumber + " finished");
    }
}
