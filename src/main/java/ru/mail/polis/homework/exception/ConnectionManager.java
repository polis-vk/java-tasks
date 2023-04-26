package ru.mail.polis.homework.exception;

import java.util.Map;

public class ConnectionManager {
    private final Map<Integer, Robot> robots;

    public ConnectionManager(Map<Integer, Robot> robots){
        this.robots = robots;
    }

    public RobotConnection getConnection(int robotId) throws RobotConnectionException{
        Robot robot = robots.get(robotId);
        if (robot == null){
            throw new RobotConnectionException("Connection error");
        }
        return new Connection(robot);
    }
}
