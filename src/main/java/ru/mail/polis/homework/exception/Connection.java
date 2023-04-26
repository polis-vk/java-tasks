package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {

    private final Robot robot;
    private boolean isConnected;

    public Connection(Robot robot) {
        this.robot = robot;
        isConnected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionFailedException {
        if (!isConnected) {
            throw new RobotConnectionFailedException("Couldn't establish connection");
        }
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() {
        isConnected = false;
    }
}
