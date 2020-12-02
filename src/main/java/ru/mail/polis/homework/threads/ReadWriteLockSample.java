package ru.mail.polis.homework.threads;

import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ReadWriteLockSample {
  private final Semaphore semaphore;
  private final int maxNumber;

  public ReadWriteLockSample(int num) {
    semaphore = new Semaphore(num, true);
    maxNumber = num;
  }

  public void readLock() {
    try {
      semaphore.acquire(1);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void writeLock() {
    try {
      semaphore.acquire(maxNumber);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  public void readUnlock() {
    semaphore.release(1);
  }

  public void writeUnlock() {
    semaphore.release(maxNumber);
  }
}

class MyThread {
  ReadWriteLockSample readWriteLock = new ReadWriteLockSample(10);

  public void get() {

    readWriteLock.readLock();
    try {

      System.out.println("Start read " + Thread.currentThread().getName());
      Thread.sleep(2000);
      System.out.println("End read " + Thread.currentThread().getName());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      readWriteLock.readUnlock();
    }

  }

  public void put() {

    readWriteLock.writeLock();
    try {
      System.out.println("Start write " + Thread.currentThread().getName());
      Thread.sleep(2000);
      System.out.println("End write " + Thread.currentThread().getName());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      readWriteLock.writeUnlock();
    }
  }

}

class Start {
  public static void main(String[] args) {
    MyThread example = new MyThread();

    Executors.newFixedThreadPool(11);

    new Thread(example::put).start();
    new Thread(example::put).start();

    new Thread(example::get).start();
    new Thread(example::get).start();
    new Thread(example::get).start();
    new Thread(example::put).start();
    new Thread(example::get).start();
    new Thread(example::get).start();

    new Thread(example::put).start();

    new Thread(example::get).start();
    new Thread(example::get).start();
    new Thread(example::get).start();
    new Thread(example::get).start();
    new Thread(example::get).start();
  }
}

