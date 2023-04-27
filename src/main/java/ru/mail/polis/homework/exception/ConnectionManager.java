package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {

    private final Map<Integer, Robot> robots = new HashMap<>();

    public ConnectionManager(Robot[] robots) {
        for (Robot robot : robots) {
            this.robots.put(robot.getId(), robot);
        }
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new RobotConnectionException("Can't find robot by Id");
        }
        return new Connection(robot);
    }
}
