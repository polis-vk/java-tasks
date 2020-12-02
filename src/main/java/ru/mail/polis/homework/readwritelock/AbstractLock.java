package ru.mail.polis.homework.readwritelock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class AbstractLock implements Lock {

    @Override
    public void lock() {
    }

    @Override
    public void unlock() {

    }

    @SuppressWarnings("unused")
    @Override
    public void lockInterruptibly() {
    }

    @SuppressWarnings("unused")
    @Override
    public boolean tryLock() {
        return false;
    }

    @SuppressWarnings("unused")
    @Override
    public boolean tryLock(long time, TimeUnit unit) {
        return false;
    }

    @SuppressWarnings("unused")
    @Override
    public Condition newCondition() {
        return null;
    }
}
