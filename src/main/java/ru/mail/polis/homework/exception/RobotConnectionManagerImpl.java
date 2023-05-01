package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotConnectionManagerImpl implements RobotConnectionManager {
    private final Map<Integer, Robot> robots;

    public RobotConnectionManagerImpl(List<Robot> robots) {
        this.robots = new HashMap<>();
        for (Robot robot : robots) {
            this.robots.put(robot.getRobotId(), robot);
        }
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new RobotConnectionException("No such robot exists");
        }
        return new RobotConnectionImpl(robot);
    }
}
