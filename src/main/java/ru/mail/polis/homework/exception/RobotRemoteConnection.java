package ru.mail.polis.homework.exception;

import java.util.Random;

public class RobotRemoteConnection implements RobotConnection {
    private final Robot robot;
    private boolean isConnected;

    public RobotRemoteConnection(Robot robot) {
        this.robot = robot;
        isConnected = new Random().nextInt(10) > 5;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotException {
        if (!isConnected) throw new RobotException();
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() {
        isConnected = false;
    }
}
