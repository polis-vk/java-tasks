package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {

    Map<Integer, Robot> robotsMap;

    public ConnectionManager(List<Robot> robotsList) {
        robotsMap = new HashMap<>();
        robotsList.forEach(robot -> robotsMap.put(robot.getId(), robot));
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionFailedException {
        Robot robot = robotsMap.get(robotId);
        if (robot == null) {
            throw new RobotConnectionFailedException("No robot with such id");
        }
        return new Connection(robot);
    }
}
