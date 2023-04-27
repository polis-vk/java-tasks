package ru.mail.polis.homework.exception;

import java.util.Map;

public class RobotConnManager implements RobotConnectionManager {

    private final Map<Integer, Robot> robots;

    public RobotConnManager(Map<Integer, Robot> robots) {
        this.robots = robots;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws ConnException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new ConnException();
        }
        return new RobotConn(robot);
    }
}