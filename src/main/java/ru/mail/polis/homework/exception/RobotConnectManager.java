package ru.mail.polis.homework.exception;

public class RobotConnectManager implements RobotConnectionManager {
    @Override
    public RobotConnection getConnection(int robotId, Robot robot) throws RobotConnectException {
        if (robot == null) {
            throw new RobotConnectException("The attempt to connect robot failed");
        }
        return new RobotConnect(robot);
    }
}
