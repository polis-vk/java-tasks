package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {

    private Robot robot;
    private boolean opened;

    public Connection(Robot robot) {
        this.robot = robot;
        opened = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!opened) {
            throw new RobotConnectionException("Connection failed");
        }
        Direction targetDirection;
        targetDirection = robot.getX() < x ? Direction.RIGHT : Direction.LEFT;
        while (robot.direction != targetDirection) {
            robot.turnRight();
        }
        changeXCoordinate(x);
        targetDirection = robot.getY() < y ? Direction.UP : Direction.DOWN;
        while (robot.direction != targetDirection) {
            robot.turnLeft();
        }
        changeYCoordinate(y);
    }

    @Override
    public void close() {
        opened = false;
        robot = null;
    }

    private void changeXCoordinate(int x) {
        while (robot.getX() - x != 0) {
            robot.stepForward();
        }
    }

    private void changeYCoordinate(int y) {
        while (robot.getY() - y != 0) {
            robot.stepForward();
        }
    }
}
