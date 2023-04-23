package ru.mail.polis.homework.exception;

public class RobotConnector implements RobotConnection {
    private Robot robot;
    private boolean connection;

    public RobotConnector(Robot robot) {
        this.robot = robot;
        connection = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!connection) {
            throw new RobotConnectionException("Connection failed");
        } else {
            robot.moveTo(x, y);
        }
    }

    @Override
    public void close() {
        connection = false;
    }
}
