package ru.mail.polis.homework.exception;

public class RobotConnectionManager implements ConnectionManager{
    @Override
    public RobotConnection getConnection(int robotId) {
        Robot robot = RobotRemoteControl.getRobot(robotId);
        if (robot == null){
            throw new RobotConnectionException("Failed to connect to robot");
        }
        return new RobotConnection(robot);
    }
}
