package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.Map;

public class RobotConnectionManagerImpl implements RobotConnectionManager {
    private final Map<Integer, Robot> robotMap;

    public RobotConnectionManagerImpl() {
        robotMap = new HashMap<Integer, Robot>();
    }

    public RobotConnectionManagerImpl(Map<Integer, Robot> map) {
        robotMap = map;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws ConnectException {
        Robot robot = robotMap.get(robotId);
        if (robot == null) {
            throw new ConnectException("No robot with this Id");
        }
        return new RobotConnectionImpl(robot);
    }

    @Override
    public void addRobot(Robot robot) {
        robotMap.put(robot.getId(), robot);
    }
}
