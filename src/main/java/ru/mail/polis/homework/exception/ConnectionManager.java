package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {

    private final Map<Integer, Robot> robots;

    public ConnectionManager(List<Robot> robots) {
        this.robots = new HashMap<>();
        for (Robot robot : robots) {
            this.robots.put(robot.getRobotId(), robot);
        }
    }

    @Override
    public RobotConnection getConnection(int robotId) throws ConnectionFailedException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new ConnectionFailedException("Connection failed");
        }
        return new Connection(robot);
    }
}
