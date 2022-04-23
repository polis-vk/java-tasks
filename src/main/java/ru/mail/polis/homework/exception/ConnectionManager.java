package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {

    private Map<Integer, Connection> connections;

    public ConnectionManager() {
        Map<Integer, Connection> connections = new HashMap<>();
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
            Connection connection = connections.get(robotId);
            if (connection.isOpen()) {
                return connection;
            }
        }
        setConnection(robotId);
        return connections.get(robotId);
    }
}
