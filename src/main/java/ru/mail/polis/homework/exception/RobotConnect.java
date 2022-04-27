package ru.mail.polis.homework.exception;

public class RobotConnect implements RobotConnection {
    private Robot robot;
    private boolean connected;

    public RobotConnect(Robot robot) {
        this.robot = robot;
        connected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectException {
        if (!connected) {
            throw new RobotConnectException("There is no connection with robot");
        }
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() {
        robot = null;
        connected = false;
    }
}
