package ru.mail.polis.homework.exception;

import java.util.Map;

public class RobotConnectionManagerImpl implements RobotConnectionManager {
    private final Map<Integer, Robot> robots;

    public RobotConnectionManagerImpl(Map<Integer, Robot> connections) {
        this.robots = connections;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotException {
        if (!robots.containsKey(robotId)) {
            throw new RobotException("robot with such Id doesn't exist");
        }
        Robot robot = robots.get(robotId);
        if (robot.isBroken()) {
            throw new RobotException("robot with such Id is broken");
        }
        return new RobotConnectionImpl(robot);
    }
}
