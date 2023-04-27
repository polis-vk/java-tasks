package ru.mail.polis.homework.exception;

public class RobotConnectionRealization implements RobotConnection {
    private final Robot robot;
    private boolean connection;

    RobotConnectionRealization(Robot robot) {
        this.robot = robot;
        connection = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!connection) {
            throw new RobotConnectionException("Отсутствует соединение с роботом");
        }
        robot.setCoordinate(x, y);
    }

    @Override
    public void close() {
        connection = false;
    }
}
