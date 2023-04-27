package ru.mail.polis.homework.exception;

import java.util.Map;

public class RobotConnectionManagerImpl implements RobotConnectionManager {

    private final Map<Integer, Robot> connections;

    public RobotConnectionManagerImpl(Map<Integer, Robot> connections) {
        this.connections = connections;
    }

    @Override
    public RobotConnection getConnection(int robotId) {
        Robot robot = connections.get(robotId);
        if (robot == null) {
            throw new IllegalArgumentException();
        }
        return new RobotConnectionImpl(robot);
    }

}
