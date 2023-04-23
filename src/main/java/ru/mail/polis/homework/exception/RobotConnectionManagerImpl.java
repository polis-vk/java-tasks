package ru.mail.polis.homework.exception;

import java.util.Map;

public class RobotConnectionManagerImpl implements RobotConnectionManager {

    private final Map<Integer, Robot> robots;

    public RobotConnectionManagerImpl(Map<Integer, Robot> robots) {
        this.robots = robots;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws ConnectionException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new ConnectionException("No robots avaliable.");
        }
        return new RobotConnectionImpl(robot);
    }
}
