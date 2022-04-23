package ru.mail.polis.homework.exception;

public class RobotConnectionException extends Exception {

    public RobotConnectionException() {
        super();
    }

    public RobotConnectionException(String txt) {
        super(txt);
    }

    public RobotConnectionException(String txt, Throwable cause) {
        super(txt, cause);
    }
}

