package ru.mail.polis.homework.exception;

public class RobotRemoteConnection implements RobotConnection {
    private Robot robot;
    private boolean isConnected;

    public RobotRemoteConnection(Robot robot) {
        this.robot = robot;
        isConnected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!isConnected) {
            throw new RobotConnectionException("ERROR : no connection to the robot");
        }
        robot.move(x, y);
    }

    @Override
    public void close() {
        robot = null;
        isConnected = false;
    }
}