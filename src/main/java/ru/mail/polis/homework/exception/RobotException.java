package ru.mail.polis.homework.exception;

public class RobotException extends Exception {
    public RobotException(String message) {
        super(message);
    }

    public RobotException() {
        super("Connection Lost");
    }
}
