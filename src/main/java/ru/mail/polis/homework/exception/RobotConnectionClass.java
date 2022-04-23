package ru.mail.polis.homework.exception;

public class RobotConnectionClass implements RobotConnection{
    Robot robot;
    Boolean isConnect;

    RobotConnectionClass(Robot robot){
        this.robot = robot;
        isConnect = true;
    }
    @Override
    public void moveRobotTo(int x, int y) {
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() {
        isConnect = false;
    }
}
