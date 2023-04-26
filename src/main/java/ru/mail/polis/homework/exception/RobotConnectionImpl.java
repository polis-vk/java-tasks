package ru.mail.polis.homework.exception;

public class RobotConnectionImpl implements RobotConnection {

    private Robot robot;

    public RobotConnectionImpl(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotException {
        if (robot.getX() != x) {
            Robot.RobotMoveDirections directionX = (x > robot.getX() ?
                    Robot.RobotMoveDirections.EAST : Robot.RobotMoveDirections.WEST);
            while (robot.getX() != x) {
                robot.move(directionX);
            }
        }

        if (robot.getY() != y) {
            Robot.RobotMoveDirections directionY = (y > robot.getY() ?
                    Robot.RobotMoveDirections.SOUTH : Robot.RobotMoveDirections.NORTH);
            while (robot.getY() != y) {
                robot.move(directionY);
            }
        }
    }

    @Override
    public void trySelfRepair() {
        robot.trySelfRepair();
    }

    @Override
    public void close() {
        this.robot = null;
    }

}
