package ru.mail.polis.homework.exception;

public class ConnectionFailedException extends Exception {
    public ConnectionFailedException() {
        super("ConnectionFailed");
    }

    public ConnectionFailedException(String message) {
        super(message);
    }

    public ConnectionFailedException(String message, Throwable e) {
        super(message, e);
    }
}
