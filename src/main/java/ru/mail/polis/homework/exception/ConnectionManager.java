package ru.mail.polis.homework.exception;

import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {
    private final Map<Integer, Robot> robotsMap;

    public ConnectionManager(Map<Integer, Robot> robotsMap) {
        this.robotsMap = robotsMap;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = robotsMap.get(robotId);
        if (robot == null) {
            throw new RobotConnectionException("Incorrect id");
        }
        return new RobotConnector(robotsMap.get(robotId));
    }
}
