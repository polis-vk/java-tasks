package ru.mail.polis.homework.exception;

public class RobotConnectionImpl implements RobotConnection {

    private Robot robot;

    public RobotConnectionImpl(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void moveRobotTo(int x, int y) throws ConnectException {
        if (robot == null) {
            throw new ConnectException();
        }
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() throws ConnectException {
        this.robot = null;
    }
}
