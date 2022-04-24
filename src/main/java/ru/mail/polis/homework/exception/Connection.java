package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private Robot robot;

    public Connection(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionFailedException {
        if (!isAlive()) {
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
        robot = null;
    }

    private boolean isAlive() {
        return robot.getPowerValue() != 0;
    }
}
