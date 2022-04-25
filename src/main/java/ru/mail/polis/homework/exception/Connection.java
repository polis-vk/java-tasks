package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {

    private Robot robot;

    public Connection(Robot robot) {
        this.robot = robot;
        robot.setConnected(true);
    }

    @Override
    public void moveRobotTo(int x, int y) throws ConnectionException {
        checkConnection();
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() throws ConnectionException {
        checkConnection();
        robot.setConnected(false);
        robot = null;
    }

    private void checkConnection() throws ConnectionException {
        if (robot == null || !robot.isConnected()) {
            throw new ConnectionException("Robot not connected");
        }
    }
}
