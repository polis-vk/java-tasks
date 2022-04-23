package ru.mail.polis.homework.exception;

public class RobotConnectionException extends Exception {

    public RobotConnectionException() {
        super("Connection has failed");
    }

    public RobotConnectionException(String message) {
        super(message);
    }
}
