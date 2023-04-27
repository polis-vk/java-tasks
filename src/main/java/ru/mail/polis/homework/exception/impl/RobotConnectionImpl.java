package ru.mail.polis.homework.exception.impl;

import ru.mail.polis.homework.exception.Robot;
import ru.mail.polis.homework.exception.RobotConnection;
import ru.mail.polis.homework.exception.exception.RobotConnectionException;

public class RobotConnectionImpl implements RobotConnection {

    private final Robot robot;
    private boolean connection;

    public RobotConnectionImpl(Robot robot) {
        this.robot = robot;
        connection = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!connection) {
            throw new RobotConnectionException("Connection with robot is lost!");
        }
        robot.setXY(x, y);
    }

    @Override
    public Robot getRobot() throws RobotConnectionException {
        if (!connection) {
            throw new RobotConnectionException("Connection with robot is lost!");
        }
        return robot;
    }

    @Override
    public void close() {
        connection = false;
    }

}
