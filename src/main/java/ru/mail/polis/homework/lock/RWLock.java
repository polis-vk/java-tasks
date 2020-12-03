package ru.mail.polis.homework.lock;

import java.util.concurrent.Semaphore;

public class RWLock {

    private static volatile boolean isWriteAwait = false;
    private static volatile int countOfReadingThreads = 0;    //count of reading threads

    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore = new Semaphore(1000, true);
        new Thread(new ReadThread(semaphore, "first reader")).start();
        new Thread(new ReadThread(semaphore, "second reader")).start();
        new Thread(new ReadThread(semaphore, "third reader")).start();

        Thread.sleep(3000);
        new Thread(new WriteThread(semaphore, "writer")).start();
        Thread.sleep(3000);

        new Thread(new ReadThread(semaphore, "fifth reader")).start();
        new Thread(new ReadThread(semaphore, "sixth reader")).start();
        new Thread(new ReadThread(semaphore, "seventh reader")).start();
    }

    private static class ReadThread extends Thread {

        Semaphore semaphore;
        String name;

        ReadThread(Semaphore semaphore, String name) {
            this.semaphore = semaphore;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(300);
                System.out.println(name + " wait acquire "
                        + Thread.currentThread().getName());
                while (isWriteAwait) {
                    Thread.sleep(1000);
                }
                semaphore.acquire(1);
                ++countOfReadingThreads;

                System.out.println(name + " start read "
                        + Thread.currentThread().getName());
                Thread.sleep(5000);
                System.out.println(name + " finish read "
                        + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "end thread " +
                    Thread.currentThread().getName() + " CORO " + countOfReadingThreads);
            semaphore.release(1);
            --countOfReadingThreads;

        }
    }

    private static class WriteThread extends Thread {

        Semaphore semaphore;
        String name;

        WriteThread(Semaphore semaphore, String name) {
            this.semaphore = semaphore;
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(300);
                System.out.println(name + " wait acquire "
                        + Thread.currentThread().getName());
                while (countOfReadingThreads > 0) {
                    Thread.sleep(10);
                }
                while (isWriteAwait) {
                    Thread.sleep(10);
                }
                isWriteAwait = true;

                semaphore.acquire(1000);

                System.out.println(name + " start write "
                        + Thread.currentThread().getName());
                Thread.sleep(5000);
                System.out.println(name + " finish write "
                        + Thread.currentThread().getName());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name + "end thread " +
                    Thread.currentThread().getName());
            semaphore.release(1000);
            isWriteAwait = false;
        }
    }
}
