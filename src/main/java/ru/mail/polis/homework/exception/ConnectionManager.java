package ru.mail.polis.homework.exception;

import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {

    private final Map<Integer, Robot> poolRobots;

    public ConnectionManager(Map<Integer, Robot> poolRobots) {
        this.poolRobots = poolRobots;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = poolRobots.get(robotId);

        if (robot == null) {
            throw new RobotConnectionException("Робота с таким id нет, попробуйте указать другой id");
        }

        return new Connection(robot);
    }
}
