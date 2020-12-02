package ru.mail.polis.homework.threads;

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

