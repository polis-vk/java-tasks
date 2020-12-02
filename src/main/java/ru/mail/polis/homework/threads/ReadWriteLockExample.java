package ru.mail.polis.homework.threads;

public class ReadWriteLockExample {
  ReadWriteLockSample readWriteLock = new ReadWriteLockSample(10);

  public void get() {

    ReadWriteLockSample.ReadLock lock = readWriteLock.readLock();
    lock.lock();
    try {

      System.out.println("Start read " + Thread.currentThread().getName());
      Thread.sleep(2000);
      System.out.println("End read " + Thread.currentThread().getName());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
     lock.unlock();
    }

  }

  public void put() {

    ReadWriteLockSample.WriteLock lock = readWriteLock.writeLock();
    lock.lock();
    try {
      System.out.println("Start write " + Thread.currentThread().getName());
      Thread.sleep(2000);
      System.out.println("End write " + Thread.currentThread().getName());
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
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

