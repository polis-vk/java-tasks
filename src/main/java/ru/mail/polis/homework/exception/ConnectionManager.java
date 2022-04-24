package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {

    private final Map<Integer, Robot> robots = new HashMap<>();

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new RobotConnectionException("Connection failed");
        }
        return new Connection(robot);
    }
}
