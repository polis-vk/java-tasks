package ru.mail.polis.homework.exception;

public class NoConnectionException extends Exception {
    NoConnectionException() {
        super();
    }

    NoConnectionException(String description) {
        super(description);
    }
}
