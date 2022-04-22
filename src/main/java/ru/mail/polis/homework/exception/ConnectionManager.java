package ru.mail.polis.homework.exception;

public class ConnectionManager implements RobotConnectionManager {
    @Override
    public RobotConnection getConnection(int robotId) throws ConnectionFailedException {
        Robot robot = RobotRemoteControl.getRobotById(robotId);
        if (robot == null) {
            throw new ConnectionFailedException("Connection failed");
        }
        return new Connection(robot);
    }
}
