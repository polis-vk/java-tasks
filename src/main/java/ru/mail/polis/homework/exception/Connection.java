package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private Robot robot;
    private boolean isConnected;
    public Connection(Robot robot) {
        this.robot = robot;
        this.isConnected = true;
    }
    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!isConnected) {
            throw new RobotConnectionException("No connection with robot!");
        }
        robot.move(x, y);
    }
    @Override
    public void visitRobotParty() throws RobotConnectionException {
        if (!isConnected) {
            throw new RobotConnectionException("No connection with robot!");
        }
        robot.dance();
    }
    @Override
    public void close() {
        robot = null;
        isConnected = false;
    }
}