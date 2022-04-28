package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {

    private final Map<Integer, RobotConnection> connections;

    public ConnectionManager() {
        connections = new HashMap<>();
    }

    public void setConnection(int robotId) throws RobotConnectionException {
        RobotConnection connection = new Connection(robotId);
        if (connections.containsKey(robotId)) {
            throw new RobotConnectionException("The connection has already been established!");
        }
        connections.put(robotId, connection);
    }

    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        RobotConnection connection = connections.get(robotId);
        if (connection != null) {
            return connection;
        }
        setConnection(robotId);
        return connections.get(robotId);
    }

    public void closeConnection(int robotId) throws RobotConnectionException {
        RobotConnection connection = connections.get(robotId);
        if (connection == null) {
            throw new RobotConnectionException("The connection is already closed!");
        }
        connection.close();
        connections.remove(robotId);
    }
}
