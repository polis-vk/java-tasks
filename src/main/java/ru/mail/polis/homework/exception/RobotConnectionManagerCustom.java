package ru.mail.polis.homework.exception;

import java.util.List;

public class RobotConnectionManagerCustom implements RobotConnectionManager {

    private final List<Robot> allRobots;

    public RobotConnectionManagerCustom(List<Robot> allRobots) {
        this.allRobots = allRobots;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotNotFoundException {
        Robot robot = allRobots.stream().filter(robot1 -> robot1.getId() == robotId).findFirst().orElse(null);
        if (robot == null) {
            throw new RobotNotFoundException("Robot with id =" + robotId + " not found");
        }
        return new RobotConnectionCustom(robot);
    }
}
