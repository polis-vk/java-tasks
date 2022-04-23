package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private Robot r2d2;
    private boolean connectionStatus;

    public boolean getConnectionStatus() {
        return connectionStatus;
    }

    public Connection(Robot robot, boolean connectionStatus) {
        this.r2d2 = robot;
        this.connectionStatus = connectionStatus;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (connectionStatus) {
            r2d2.setxCoord(x);
            r2d2.setyCoord(y);
        } else {
            throw new RobotConnectionException("Connection error!");
        }
    }

    @Override
    public void close() {
        connectionStatus = false;
        r2d2 = null;
    }
}
