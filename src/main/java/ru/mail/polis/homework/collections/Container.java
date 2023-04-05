package ru.mail.polis.homework.collections;

public class Container<T> {
    private T value;

    Container() {
        value = null;
    }

    public T get() {
        return value;
    }

    public void set(T value) {
        this.value = value;
    }
}