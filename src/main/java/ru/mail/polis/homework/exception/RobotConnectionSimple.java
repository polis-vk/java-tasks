package ru.mail.polis.homework.exception;


public class RobotConnectionSimple implements RobotConnection {
    private Robot connectedRobot;

    public RobotConnectionSimple(Robot robot) {
        connectedRobot = robot;
    }

    @Override
    public void moveRobotTo(int x, int y) throws ConnectionException {
        connectedRobot.connect();
        connectedRobot.move(x, y);
    }

    @Override
    public void close() {
        connectedRobot.disConnect();
        connectedRobot = null;
    }
}