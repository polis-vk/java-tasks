package ru.mail.polis.homework.exception;

import java.util.List;

public class RobotRemoteConnectionManager implements RobotConnectionManager {
    private final List<Robot> robots;

    public RobotRemoteConnectionManager(List<Robot> robots) {
        this.robots = robots;
    }

    @Override
    public RobotConnection getConnection(int robotId) {
        return new RobotRemoteConnection(robots.get(robotId));
    }
}
