package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {
    private final Map<Integer, Robot> robots = new HashMap<>();

    public ConnectionManager(List<Robot> robots) {
        for (Robot robot : robots) {
            this.robots.putIfAbsent(robot.getID(), robot);
        }
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new RobotConnectionException("Wrong ID");
        }
        return new Connection(robot);
    }
}
