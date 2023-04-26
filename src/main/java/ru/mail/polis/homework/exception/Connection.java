package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private Robot robot;
    private boolean isConnected;

    public Connection(Robot robot) {
        this.robot = robot;
        isConnected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (robot == null || !isConnected) {
            throw new RobotConnectionException("Connection error!");
        }
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() {
        robot = null;
        isConnected = false;
    }
}
