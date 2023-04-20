package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {

    private final Robot robot;
    private boolean isOpened;
    Connection(Robot robot) {
        this.robot = robot;
        this.isOpened = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!isOpened) {
            throw new RobotConnectionException("Connection was closed");
        }
        this.robot.setY(y);
        this.robot.setX(x);
    }

    @Override
    public void close() {
        this.isOpened = false;
    }
}
