package ru.mail.polis.homework.read_write_lock;

import java.util.ArrayList;

public class ReadWriteLockExample {
    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReadWriteLock();
        int numberOfThreads = 20;
        ArrayList<Thread> threads = new ArrayList<>(numberOfThreads);
        int currentNumberOfReaders = 0;
        int currentNumberOfWriters = 0;
        for (int i = 0; i < numberOfThreads; i++) {
            if (i % 2 == 0) {
                threads.add(new Thread(new Reader(currentNumberOfReaders, readWriteLock)));
                currentNumberOfReaders++;
            } else {
                threads.add(new Thread(new Writer(currentNumberOfWriters, readWriteLock)));
                currentNumberOfWriters++;
            }
        }
        for (Thread thread : threads) {
            thread.start();
        }
    }
}
