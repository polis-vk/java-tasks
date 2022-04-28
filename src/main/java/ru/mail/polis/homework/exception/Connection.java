package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private Robot robot;

    public Connection(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void moveRobotTo(double x, double y) {
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() {
        robot.changeStatus(false);
        robot = null;
    }

}

