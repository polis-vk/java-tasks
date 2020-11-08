package ru.mail.polis.homework.io.objects;

import java.io.IOException;

@FunctionalInterface
public interface TestConsumer {
    void accept() throws IOException;
}