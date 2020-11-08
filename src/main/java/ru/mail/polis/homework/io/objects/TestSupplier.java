package ru.mail.polis.homework.io.objects;

import java.io.IOException;

@FunctionalInterface
public interface TestSupplier<T> {
    T get() throws IOException, ClassNotFoundException;
}
