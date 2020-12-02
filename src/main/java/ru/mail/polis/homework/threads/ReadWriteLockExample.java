package ru.mail.polis.homework.threads;

public class ReadWriteLockExample {
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

  public static void main(String[] args) {
    ReadWriteLockExample example = new ReadWriteLockExample();

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

