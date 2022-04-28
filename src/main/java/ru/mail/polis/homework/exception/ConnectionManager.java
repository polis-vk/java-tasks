package ru.mail.polis.homework.exception;

import java.util.Map;

public class ConnectionManager implements RobotConnectionManager{

    private final Map<Integer, Robot> robots;

    public ConnectionManager(Map<Integer, Robot> robots) {
        this.robots = robots;
    }


    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = robots.get(robotId);

        if (robot == null) {
            throw new RobotConnectionException("Robot not found");
        }

        return new Connection(robot);
    }
}
