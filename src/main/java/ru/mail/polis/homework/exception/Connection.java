package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private Robot robot;

    public Connection(Robot robot) throws RobotEstablishConnectionException {
        if (robot == null) {
            throw new IllegalArgumentException("Invalid argument passed, which is null");
        }
        if (robot.getPowerValue() == 0) {
            throw new RobotEstablishConnectionException("This robot is discharged, it was not possible to connect to it");
        }
        if (robot.getRobotStatusConnection()) {
            throw new RobotEstablishConnectionException("This robot already belongs to another connection");
        }
        this.robot = robot;
        this.robot.connect();
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionFailedException {
        if (!isAlive() || !robot.getRobotStatusConnection()) {
            throw new RobotConnectionFailedException("The connection with the robot is broken");
        }
        try {
            robot.setPoint(x, y);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        robot.disconnect();
        robot = null;
    }

    private boolean isAlive() {
        if (robot == null) {
            return false;
        }
        return robot.getPowerValue() != 0;
    }
}
