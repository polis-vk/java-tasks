package ru.mail.polis.homework.exception;

import java.util.Arrays;


public class RobotConnectionManagerSimple implements RobotConnectionManager {
    private final Robot[] robots;

    public RobotConnectionManagerSimple(Robot[] robots) {
        this.robots = Arrays.copyOf(robots, robots.length);
    }

    private Robot findRobotById(int robotId) throws RobotIdException {
        for (Robot robot : robots) {
            if (robot.getRobotId() == robotId) {
                return robot;
            }
        }
        throw new RobotIdException("No robot with the specified id was found");
    }


    @Override
    public RobotConnection getConnection(int robotId) throws ConnectionException {
        try {
            Robot robot = findRobotById(robotId);
            if (robot.isConnected()) {
                throw new ConnectionException("Robot is connected to another device");
            }
            return new RobotConnectionSimple(robot);
        } catch (RobotIdException idException) {
            throw new ConnectionException(new RobotIdException(idException.getMessage()));
        }
    }
}