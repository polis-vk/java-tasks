package ru.mail.polis.homework.exception;

import java.util.Map;

public class ConnectionManager {
    private final Map<Integer, Robot> robots;

    public ConnectionManager(Map<Integer, Robot> robots) {
        this.robots = robots;
    }

    public RobotConnection getConnection(int robotId) throws ConnectionException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new ConnectionException("Getting connection error");
        }
        return new Connection(robot);
    }
}
