package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.List;

public class RobotConnectionManagerCustom implements RobotConnectionManager {

    //private final List<Robot> allRobots;
    private final HashMap<Integer, Robot> allRobots = new HashMap<>();

    protected RobotConnectionManagerCustom(List<Robot> allRobots) {
        for (Robot current : allRobots) {
            this.allRobots.put(current.getId(), current);
        }
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotNotFoundException {
        Robot robot = allRobots.get(robotId);
        if (robot == null) {
            throw new RobotNotFoundException("Robot with id = " + robotId + " not found");
        }
        return new RobotConnectionCustom(robot);
    }
}
