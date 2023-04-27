package ru.mail.polis.homework.io;

public class MutableInt {
    private int value;

    public MutableInt() {
        value = 0;
    }

    public MutableInt(int value) {
        this.value = value;
    }

    public void increment() {
        value++;
    }

    public int getAndIncrement() {
        return value++;
    }

    public int incrementAndGet() {
        return ++value;
    }

    public void decrement() {
        value--;
    }

    public int getAndDecrement() {
        return value--;
    }

    public int decrementAndGet() {
        return --value;
    }

    public void add(int operand) {
        value += operand;
    }

    public void remove(int operand) {
        value -= operand;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.valueOf(getValue());
    }
}
