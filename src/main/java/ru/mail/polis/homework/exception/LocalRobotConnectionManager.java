package ru.mail.polis.homework.exception;

import java.util.Map;

public class LocalRobotConnectionManager implements RobotConnectionManager {
    private final Map<Integer, Robot> robots;

    public LocalRobotConnectionManager(Map<Integer, Robot> robots) {
        this.robots = robots;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws ConnectionException {
        if (robots.containsKey(robotId)) {
            return new LocalRobotConnection(robots.get(robotId), 5);
        }
        throw new ConnectionException("No such robot available");
    }
}
