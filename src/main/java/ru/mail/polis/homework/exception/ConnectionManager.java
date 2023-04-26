package ru.mail.polis.homework.exception;

import java.util.HashMap;

public class ConnectionManager implements RobotConnectionManager {
    private final HashMap<Integer, Robot> robots;

    public ConnectionManager(HashMap<Integer, Robot> robots) {
        this.robots = robots;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        if (!robots.containsKey(robotId)) {
            throw new RobotConnectionException("Incorrect id");
        }

        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new RobotConnectionException("Connection error!");
        }
        return new Connection(robot);
    }
}
