package ru.mail.polis.homework.exception;

import java.net.ConnectException;

public class ConnectionManager implements RobotConnectionManager {
    @Override
    public RobotConnection getConnection(int robotId) throws ConnectException {
        return new Connection().setConnection(robotId);
    }
}
