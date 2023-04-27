package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {
    private final Map<Integer, Robot> robots = new HashMap<>();

    ConnectionManager(List<Robot> robots) {
        for (Robot robot : robots) {
            this.robots.put(robot.getId(), robot);
        }
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new RobotConnectionException("Не найден робот " + robotId);
        }
        return new Connection(robot);
    }
}
