package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.Map;

public class RobotConnectionManagerImpl implements RobotConnectionManager {

    private final Map<Integer, RobotConnection> connections;

    public RobotConnectionManagerImpl() {
        this.connections = new HashMap<>();
    }

    public RobotConnectionManagerImpl(Map<Integer, RobotConnection> connections) {
        this.connections = connections;
    }

    @Override
    public void createConnection(int robotId) {
        connections.put(robotId, new RobotConnectionImpl(new Robot()));
    }

    @Override
    public RobotConnection getConnection(int robotId) {
        return connections.get(robotId);
    }

}
