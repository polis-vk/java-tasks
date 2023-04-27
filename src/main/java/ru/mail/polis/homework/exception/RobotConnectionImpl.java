package ru.mail.polis.homework.exception;

public class RobotConnectionImpl implements RobotConnection {

    private final Robot robot;
    private boolean connected;

    public RobotConnectionImpl(Robot robot) {
        this.robot = robot;
        this.connected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        checkConnection();
        robot.move(x, y);
    }

    @Override
    public Robot getRobot() throws RobotConnectionException {
        checkConnection();
        return robot;
    }

    @Override
    public void close() {
        connected = false;
    }

    private void checkConnection() throws RobotConnectionException {
        if (!connected) {
            throw new RobotConnectionException("Robot connection lost!");
        }
    }
}
