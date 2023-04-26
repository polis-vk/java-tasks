package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection{
    private Robot robot;
    private boolean isConnected;

    public Connection(Robot robot){
        this.robot = robot;
        this.isConnected = true;
    }

    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!isConnected){
            throw new RobotConnectionException("Connection error");
        }
        robot.moveTo(x, y);
    }

    @Override
    public void close() {
        isConnected = false;
    }
}
