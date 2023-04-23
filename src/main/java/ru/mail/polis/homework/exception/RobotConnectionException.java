package ru.mail.polis.homework.exception;

public class RobotConnectionException extends Exception {
    public RobotConnectionException() {
        super();
    }

    public RobotConnectionException(String message) {
        super(message);
    }

    public RobotConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
