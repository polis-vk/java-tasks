package ru.mail.polis.homework.exception.impl;

import ru.mail.polis.homework.exception.Robot;
import ru.mail.polis.homework.exception.RobotConnection;
import ru.mail.polis.homework.exception.RobotConnectionManager;
import ru.mail.polis.homework.exception.exception.ConnectionException;

import java.util.HashMap;
import java.util.Map;

public class RobotConnectionManagerImpl implements RobotConnectionManager {

    private final Map<Integer, RobotConnection> robotConnections;

    public RobotConnectionManagerImpl() {
        robotConnections = new HashMap<>();
    }

    public RobotConnectionManagerImpl(Map<Integer, RobotConnection> robotConnections) {
        this.robotConnections = robotConnections;
    }

    @Override
    public RobotConnection getConnection(int robotId) {
        return robotConnections.get(robotId);
    }

    @Override
    public void createConnection(int robotId) {
        robotConnections.put(robotId, new RobotConnectionImpl(new Robot()));
    }

}
