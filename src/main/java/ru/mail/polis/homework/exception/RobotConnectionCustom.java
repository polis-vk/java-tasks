package ru.mail.polis.homework.exception;

public class RobotConnectionCustom implements RobotConnection {
    private final Robot robot;
    private boolean connection;

    public RobotConnectionCustom(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!connection) {
            throw new RobotConnectionException("Connection exception", robot.getId());
        }
        robot.moveTo(x, y);
    }

    @Override
    public boolean connection() {
        return this.connection;
    }

    @Override
    public void close() {
        connection = false;
    }
}
