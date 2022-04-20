package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {

    private Robot robot;
    private boolean isConnected;

    @Override
    public void moveRobotTo(int x, int y) throws ConnectionException {
        if (!isConnected || robot == null) {
            throw new ConnectionException("Сначала установите соединение с роботом, возможно ваше старое соединение было разорвано");
        }

        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void connectionToRobot(Robot robot) throws ConnectionException {
        if (isConnected || this.robot != null) {
            throw new ConnectionException("Это соединение уже работает с другим роботом, создайте новое");
        }

        isConnected = true;
        this.robot = robot;
    }

    @Override
    public void close() {
        isConnected = false;
        robot = null;
    }
}
