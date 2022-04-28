package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private Robot robot;
    private boolean isConnected;

    public Connection(Robot robot) {
        this.robot = robot;
        isConnected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotException {
        if (!isConnected) {
            throw new RobotException();
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
