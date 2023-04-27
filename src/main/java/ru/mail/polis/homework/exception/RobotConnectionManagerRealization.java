package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class RobotConnectionManagerRealization implements RobotConnectionManager {
    private Map<Integer, Robot> robots = new HashMap<>();

    RobotConnectionManagerRealization(List<Robot> robots) {
        for (Robot robot : robots) {
            this.robots.put(robot.getModelNumber(), robot);
        }
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new RobotConnectionException("Такого робота не существует");
        }
        return new RobotConnectionRealization(robot);
    }
}
