package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private final Robot robot;
    private boolean active;

    Connection(Robot robot) {
        this.robot = robot;
        active = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!active) {
            throw new RobotConnectionException("No connection with the robot" + robot.getId());
        }
        robot.setPosition(x, y);
    }

    @Override
    public void close() {
        active = false;
    }
}
