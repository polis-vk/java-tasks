package ru.mail.polis.homework.exception;

public class RobotConnectionException extends Exception {
    public RobotConnectionException() {
        super("ConnectionFailed");
    }

    public RobotConnectionException(String message) {
        super(message);
    }

//    public RobotConnectionException(String message, Throwable e) {
//        super(message, e);
//    }
}
