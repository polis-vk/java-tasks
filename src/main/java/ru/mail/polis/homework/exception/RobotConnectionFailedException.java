package ru.mail.polis.homework.exception;

public class RobotConnectionFailedException extends Exception {

    public RobotConnectionFailedException() {
        super();
    }

    public RobotConnectionFailedException(String message) {
        super(message);
    }

    public RobotConnectionFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
