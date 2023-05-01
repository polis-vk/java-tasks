package ru.mail.polis.homework.exception;

public class RobotConnectionImpl implements RobotConnection {
    private boolean connected;
    private final Robot robot;

    public RobotConnectionImpl(Robot robot) {
        this.robot = robot;
        connected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!connected) {
            throw new RobotConnectionException("Not connected");
        }
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() {
        connected = false;
    }
}
