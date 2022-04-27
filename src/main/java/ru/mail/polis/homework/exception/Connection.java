package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private Robot robot;

    public Connection(Robot robot) {
        this.robot = robot;
        this.robot.setConnection(true);
    }

    @Override
    public void moveRobotTo(int x, int y) throws ConnectionFailedException {
        if (!robot.isConnected()) {
            throw new ConnectionFailedException();
        }
        robot.setCoordinates(x, y);
    }

    @Override
    public void close() {
        robot.setConnection(false);
        robot = null;
    }
}