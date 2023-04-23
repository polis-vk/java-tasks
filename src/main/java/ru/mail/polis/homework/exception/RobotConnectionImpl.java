package ru.mail.polis.homework.exception;

public class RobotConnectionImpl implements RobotConnection {

    private final Robot robot;
    private boolean connected;

    public RobotConnectionImpl(Robot robot) {
        this.robot = robot;
        connected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws ConnectionException {

        if (!connected) {
            throw new ConnectionException("Connection Lost.");
        }
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() {
        connected = false;
    }
}
