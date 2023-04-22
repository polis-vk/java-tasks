package ru.mail.polis.homework.exception;

public class RobotIdException extends Exception {

    public RobotIdException() {
        super();
    }

    public RobotIdException(String message) {
        super(message);
    }

    public RobotIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
