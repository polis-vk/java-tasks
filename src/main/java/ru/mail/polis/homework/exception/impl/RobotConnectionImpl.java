package ru.mail.polis.homework.exception.impl;

import ru.mail.polis.homework.exception.Robot;
import ru.mail.polis.homework.exception.RobotConnection;
import ru.mail.polis.homework.exception.exception.ConnectionException;

public class RobotConnectionImpl implements RobotConnection {

    private final Robot robot;
    private boolean isConnected;

    public RobotConnectionImpl(Robot robot) {
        this.robot = robot;
        isConnected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws ConnectionException {
        if (!isConnected) {
            throw new ConnectionException("Connection with robot is lost!");
        }
        robot.setCoordinates(x, y);
    }

    @Override
    public Robot getRobot() throws ConnectionException {
        if (!isConnected) {
            throw new ConnectionException("Connection with robot is lost!");
        }
        return robot;
    }

    @Override
    public void close() {
        isConnected = false;
    }

}
