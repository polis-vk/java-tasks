package ru.mail.polis.homework.exception;

public class RobotConnectionClass implements RobotConnection {

    private final Robot robot;

    public RobotConnectionClass(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (robot.getX() < x) {
            while (robot.getDirection() != Direction.RIGHT) robot.turnRight();
            while (robot.getX() < x) robot.stepForward();
        }

        if (robot.getX() > x) {
            while (robot.getDirection() != Direction.LEFT) robot.turnRight();
            while (robot.getX() > x) robot.stepForward();
        }

        if (robot.getY() < y) {
            while (robot.getDirection() != Direction.UP) robot.turnLeft();
            while (robot.getY() < y) robot.stepForward();
        }

        if (robot.getY() > y) {
            while (robot.getDirection() != Direction.DOWN) robot.turnLeft();
            while (robot.getY() > y) robot.stepForward();
        }
    }

    @Override
    public void close() {

    }
}
