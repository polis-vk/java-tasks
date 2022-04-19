package ru.mail.polis.homework.exception;

import java.net.ConnectException;

public class Connection implements RobotConnection {
    private Robot robot;

    @Override
    public void moveRobotTo(int x, int y) throws ConnectException {
        if (robot == null || !robot.isConnected()) {
            throw new ConnectException("Нет соединения с роботом.");
        }
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public Connection setConnection(int robotId) throws ConnectException {
        robot = new Robot(robotId);
        if (robot.isConnected()) {
            throw new ConnectException("Соединение с этим роботом уже установлено.");
        }
        robot.setConnected(true);
        return this;
    }

    @Override
    public void close() {
        robot = null;
    }
}
