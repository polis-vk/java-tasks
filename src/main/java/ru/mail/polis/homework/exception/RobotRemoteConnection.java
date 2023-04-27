package ru.mail.polis.homework.exception;

public class RobotRemoteConnection implements RobotConnection {
    private final Robot robot;
    private boolean connected;

    public RobotRemoteConnection(Robot robot) {
        this.robot = robot;
        connected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!connected) {
            throw new RobotConnectionException("Connection is not established");
        }
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() throws RobotConnectionException {
        if (!connected) {
            throw new RobotConnectionException("Connection is not established");
        } else {
            connected = false;
        }
    }
}
