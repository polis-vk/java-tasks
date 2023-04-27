package ru.mail.polis.homework.exception;

import java.util.Map;

public class RobotRemoteConnectionManager implements RobotConnectionManager {
    private final Map<Integer, Robot> robots;

    public RobotRemoteConnectionManager(Map<Integer, Robot> robots) {
        this.robots = robots;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new RobotConnectionException("Connection already not established");
        }
        return new RobotRemoteConnection(robot);
    }
}
