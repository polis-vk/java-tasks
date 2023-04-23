package ru.mail.polis.homework.exception;

import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {
    private final Map<Integer, Robot> robots;

    public ConnectionManager(Map<Integer, Robot> connections) {
        this.robots = connections;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotException {
        if (!robots.containsKey(robotId)) {
            throw new RobotException("robot with such Id doesn't exist");
        }
        return new Connection(robots.get(robotId));
    }
}
