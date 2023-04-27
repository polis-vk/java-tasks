package ru.mail.polis.homework.exception;

import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {
    private final Map<Integer, Robot> robotMap;

    private ConnectionManager(Map<Integer, Robot> robotMap) {
        this.robotMap = robotMap;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = robotMap.get(robotId);
        if (robot == null) {
            throw new RobotConnectionException("Connection Error");
        }
        return new Connection(robot);
    }
}
