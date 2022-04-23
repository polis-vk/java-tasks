package ru.mail.polis.homework.exception;


public class Connection implements RobotConnection {
    private final Robot robot;

    public Connection(Robot robot) {
        this.robot = robot;
        robot.setConnected(true);
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!robot.isConnected()) {
            throw new RobotConnectionException("Нет соединения с роботом.");
        }
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() {
        robot.setConnected(false);
    }
}
