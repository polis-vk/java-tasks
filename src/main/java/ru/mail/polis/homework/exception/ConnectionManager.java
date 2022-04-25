package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {

    private final Map<Integer, RobotConnection> connections;

    public ConnectionManager() {
        connections = new HashMap<>();
    }

    public void setConnection(int robotId) throws RobotConnectionException {
        Connection connection = new Connection(robotId);
        if (connections.containsKey(robotId)) {
            throw new RobotConnectionException("The connection has already been established!");
        }
        connections.putIfAbsent(robotId, connection);
    }

    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        if (connections.containsKey(robotId)) {
            return connections.get(robotId);
        }
        setConnection(robotId);
        return connections.get(robotId);
    }
}
