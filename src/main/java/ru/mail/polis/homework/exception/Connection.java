package ru.mail.polis.homework.exception;

import java.net.ConnectException;

public class Connection implements RobotConnection {

    private Robot robot;

    public Connection(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotException {
        if (robot.getX() != x) {
            String directionX = (x > robot.getX() ? "east" : "west");
            while (robot.getX() != x) {
                robot.move(directionX);
            }
        }

        if (robot.getY() != y) {
            String directionY = (y > robot.getY() ? "south" : "north");
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
