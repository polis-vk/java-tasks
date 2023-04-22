package ru.mail.polis.homework.exception;

public class RobotConnectionException extends Exception {
    private final int robotId;

    public RobotConnectionException(String message, int robotId) {
        super(message);
        this.robotId = robotId;
    }

    public int getRobotId() {
        return robotId;
    }
}
