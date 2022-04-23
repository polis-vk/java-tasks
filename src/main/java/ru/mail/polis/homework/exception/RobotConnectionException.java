package ru.mail.polis.homework.exception;

import java.net.ConnectException;

public class RobotConnectionException extends ConnectException {
    public RobotConnectionException(String msg) {
        super(msg);
    }
}
