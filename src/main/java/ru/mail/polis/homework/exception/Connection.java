package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {

    private Robot robot;
    private boolean opened;

    public Connection(Robot robot) {
        this.robot = robot;
        opened = true;
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
        robot = null;
        opened = false;
    }

    private void checkConnection() throws ConnectionException {
        if (robot == null || !opened) {
            throw new ConnectionException("Robot not connected");
        }
    }
}
