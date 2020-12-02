package ru.mail.polis.homework.threads;

import java.util.concurrent.Semaphore;
public class ReadWriteLockSample {
  private final Semaphore semaphore;
  private final int maxNumber;

  private final ReadWriteLockSample.ReadLock readerLock;

  private final ReadWriteLockSample.WriteLock writerLock;

  public ReadWriteLockSample(int num) {
    semaphore = new Semaphore(num, true);
    readerLock = new ReadLock();
    writerLock = new WriteLock();
    maxNumber = num;
  }

  public class ReadLock {

    public void lock() {
      try {
        semaphore.acquire(1);
      } catch (InterruptedException e) {
        e.printStackTrace();
        unlock();
      }
    }

    public void unlock() {
      semaphore.release(1);
    }
  }

  public class WriteLock {
    public void lock() {
      try {
        semaphore.acquire(maxNumber);
      } catch (InterruptedException e) {
        e.printStackTrace();
        unlock();
      }
    }

    public void unlock() {
      semaphore.release(maxNumber);
    }

  }

  public ReadLock readLock() {
    return readerLock;
  }

  public WriteLock writeLock() {
    return writerLock;
  }
}

