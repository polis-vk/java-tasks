package ru.mail.polis.homework.exception;

public class RobotConn implements RobotConnection {
    Robot robot;
    boolean conn;

    RobotConn(Robot robot) {
        this.robot = robot;
        conn = true;
    }

    @Override
    public void moveRobotTo(int x, int y) {
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() {
        conn = false;
    }
}
