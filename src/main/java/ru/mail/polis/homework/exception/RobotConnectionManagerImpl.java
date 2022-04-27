package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.Map;

public class RobotConnectionManagerImpl implements RobotConnectionManager {

    private final static Map<Integer, Robot> robotMap = new HashMap<>();

    @Override
    public RobotConnectionImpl getConnection(int robotId) {
        Robot robot = RobotConnectionManagerImpl.getRobot(robotId);
        if (robot == null) {
            throw new RobotConnectionException("Failed to connect to robot");
        }
        return new RobotConnectionImpl(robot);
    }

    public static void addRobot(int id, Robot robot) {
        robotMap.put(id, robot);
    }

    public static Robot getRobot(int id) {
        return robotMap.get(id);
    }
}
