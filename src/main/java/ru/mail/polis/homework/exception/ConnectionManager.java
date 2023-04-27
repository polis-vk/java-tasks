package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {

    private final Map<Integer, Connection> robotConnections;

    public ConnectionManager() {
        robotConnections = new HashMap<>();
    }

    public void addRobotConnection(Robot robot) {
        robotConnections.put(robot.getRobotId(), new Connection(robot));
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Connection connection = robotConnections.get(robotId);
        if (connection == null) {
            throw new RobotConnectionException("Failed to connect!");
        }
        return connection;
    }
}
