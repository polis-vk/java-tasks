package ru.mail.polis.homework.exception;

import java.util.Map;

public class RemoteRobotConnectionManager implements RobotConnectionManager {
    private final Map<Integer, Robot> robots;

    public RemoteRobotConnectionManager(Map<Integer, Robot> robots) {
        this.robots = robots;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new RobotConnectionException("Connection already not established");
        }
        if (robot.isConnected()) {
            throw new RobotConnectionException("The connection to the robot has already been established");
        }
        return new RemoteConnectionRobot(robot);
    }
}
