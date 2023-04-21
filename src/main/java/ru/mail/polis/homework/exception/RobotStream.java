package ru.mail.polis.homework.exception;

public class RobotStream implements RobotConnection {
    private final Robot robot;
    private boolean isOpened;

    RobotStream(Robot robot) {
        if (robot != null) {
            this.robot = robot;
            isOpened = true;
        } else {
            isOpened = false;
            throw new RuntimeException("Not connected");
        }
    }

    @Override
    public void moveRobotTo(int toX, int toY) {
        if (!isOpened) {
            throw new RuntimeException("Connection is not opened");
        }
        robot.setX(toX);
        robot.setY(toY);
    }
    @Override
    public void close() {
        isOpened = false;
    }
}
