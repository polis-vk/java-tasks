package ru.mail.polis.homework.exception;

public class RobotConnectionImpl implements RobotConnection {

    private final Robot robot;
    private boolean isConnected;

    public RobotConnectionImpl(Robot robot) {
        this.robot = robot;
        this.isConnected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws ConnectionException {
        checkConnection();
        robot.setCoords(x, y);
    }

    @Override
    public Robot getRobot() throws ConnectionException {
        checkConnection();
        return robot;
    }

    @Override
    public void close() {
        isConnected = false;
    }

    private void checkConnection() throws ConnectionException {
        if (!isConnected) {
            throw new ConnectionException("Robot connection lost!");
        }
    }
}
