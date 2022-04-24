package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {

    private final Robot robot;
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
        changeXDirection(x);
        changeXCoordinate(x);
        changeYDirection(y);
        changeYCoordinate(y);
    }

    @Override
    public void close() {
        opened = false;
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

    private void changeXDirection(int x) {
        Direction targetDirection;
        if (robot.getX() < x) {
            targetDirection = Direction.RIGHT;
        } else {
            targetDirection = Direction.LEFT;
        }
        while (robot.direction != targetDirection) {
            robot.turnRight();
        }
    }

    private void changeYDirection(int y) {
        Direction targetDirection;
        if (robot.getY() < y) {
            targetDirection = Direction.UP;
        } else {
            targetDirection = Direction.DOWN;
        }
        while (robot.direction != targetDirection) {
            robot.turnLeft();
        }
    }
}
