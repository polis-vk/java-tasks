package ru.mail.polis.homework.exception.exception;

public class ConnectionException extends Exception {

    public ConnectionException() {
    }

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable cause) {
        super(message, cause);
    }

}
