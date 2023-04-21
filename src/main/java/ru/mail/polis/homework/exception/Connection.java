package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private Robot robot;
    private boolean isConnection;

    public Connection(Robot robot) {
        this.robot = robot;
        isConnection = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!isConnection) {
            throw new RobotConnectionException("Connection Exception!");
        }
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() throws RobotConnectionException {
        isConnection = false;
    }
}
