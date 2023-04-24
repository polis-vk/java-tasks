package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private boolean isConnected;
    private Robot robot;

    public Connection(Robot robot) {
        this.robot = robot;
        isConnected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws ConnectionException {
        if (!isConnected) {
            throw new ConnectionException("Not connected");
        }
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() {
        isConnected = false;
        robot = null;
    }
}
