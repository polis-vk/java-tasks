package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private Robot robot;
    private boolean isConnected;

    public Connection(Robot robot) {
        this.robot = robot;
        this.isConnected = true;
    }

    public void moveRobotTo(int x, int y) throws ConnectionException {
        if (!isConnected) {
            throw new ConnectionException("Error: robot is not connected.");
        }
        robot.moveTo(x, y);
    }

    @Override
    public void close() {
        isConnected = false;
        robot = null;
    }
}
