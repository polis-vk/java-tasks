package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RobotConnectManager implements RobotConnectionManager {
    public final Map<Integer, Robot> robots;

    public RobotConnectManager(List<Robot> robots) {
        this.robots = new HashMap<>();
        for (Robot robot : robots) {
            this.robots.put(robot.getId(), robot);
        }
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new RobotConnectException("The attempt to connect robot failed");
        }
        return new RobotConnect(robot);
    }
}
