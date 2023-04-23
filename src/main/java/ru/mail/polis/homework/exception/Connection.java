package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {

    private final Robot robot;
    private boolean isOpened;
    public Connection(Robot robot) {
        this.robot = robot;
        isOpened = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!isOpened) {
            throw new RobotConnectionException("Connection was closed");
        }
        robot.setY(y);
        robot.setX(x);
    }

    @Override
    public void close() {
        isOpened = false;
    }
}
