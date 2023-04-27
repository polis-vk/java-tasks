package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {
    private final Map<Integer, Robot> robots;

    public ConnectionManager(List<Robot> robots) {
        this.robots = new HashMap<>();
        for (Robot robot: robots){
            this.robots.put(robot.getId(), robot);
        }
    }

    public RobotConnection getConnection(int robotId) throws ConnectionException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new ConnectionException("Error: robot does not exist.");
        }
        return new Connection(robot);
    }
}
