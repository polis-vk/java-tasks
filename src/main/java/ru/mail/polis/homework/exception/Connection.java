package ru.mail.polis.homework.exception;

import java.net.ConnectException;

public class Connection implements RobotConnection {

    private Robot robot;
    private boolean isConnected;

    @Override
    public void moveRobotTo(int x, int y) throws ConnectException {
        if (!isConnected) {
            throw new ConnectException("Сначала установите соединение с роботом, возможно ваше старое соединение было разорвано");
        }

        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public Connection createConnection(Robot robot) throws ConnectException {
        if (isConnected || this.robot != null) {
            throw new ConnectException("Это соединение уже работает с другим роботом, создайте новое");
        }

        isConnected = true;
        this.robot = robot;
        return this;
    }

    @Override
    public void close() {
        isConnected = false;
        robot = null;
    }
}
