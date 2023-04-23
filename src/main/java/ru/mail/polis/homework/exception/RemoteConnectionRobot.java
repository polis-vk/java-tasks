package ru.mail.polis.homework.exception;

public class RemoteConnectionRobot implements RobotConnection {
    private final Robot robot;

    public RemoteConnectionRobot(Robot robot) {
        this.robot = robot;
        this.robot.setConnected(true);
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!robot.isConnected()) {
            throw new RobotConnectionException("Connection is not established");
        }
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() throws RobotConnectionException {
        if (!robot.isConnected()) {
            throw new RobotConnectionException("Connection is not established");
        } else {
            robot.setConnected(false);
        }
    }
}
