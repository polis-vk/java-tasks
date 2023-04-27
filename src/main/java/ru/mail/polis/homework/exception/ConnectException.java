package ru.mail.polis.homework.exception;

public class ConnectException extends Exception {
    public ConnectException() {
        super();
    }

    public ConnectException(String message) {
        super(message);
    }

    public ConnectException(String message, Throwable cause) {
        super(message, cause);
    }
}
