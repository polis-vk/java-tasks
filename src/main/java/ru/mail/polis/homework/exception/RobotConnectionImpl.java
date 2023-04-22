package ru.mail.polis.homework.exception;

public class RobotConnectionImpl implements RobotConnection {

    private Robot robot;
    private boolean isConnection = false;

    public RobotConnectionImpl(Robot robot) {
        this.robot = robot;
        isConnection = true;
        this.robot.setConnection(true);
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!isConnection) {
            throw new RobotConnectionException("No connection to robot");
        }
        robot.move(x, y);
    }

    @Override
    public void close() {
        robot.setConnection(false);
        robot = null;
        isConnection = false;
    }
}
