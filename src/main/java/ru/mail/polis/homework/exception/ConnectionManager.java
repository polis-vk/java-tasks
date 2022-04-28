package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.List;

public class ConnectionManager implements RobotConnectionManager {
    private final HashMap<Integer, Robot> robotHashMap;

    public ConnectionManager(List<Robot> robotList) {
        robotHashMap = new HashMap<Integer, Robot>();
        for (Robot robot : robotList) {
            robotHashMap.putIfAbsent(robot.getId(), robot);
        }
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotException {
        if (!robotHashMap.containsKey(robotId)) {
            throw new RobotException("This Robot does not exist");
        }
        Robot robot = robotHashMap.get(robotId);
        if (robot == null) {
            throw new RobotException("Null Pointer Exception");
        }
        return new Connection(robot);
    }
}
