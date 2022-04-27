package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private Robot r2d2;
    private boolean connectionStatus;

    public boolean getConnectionStatus() {
        return connectionStatus;
    }

    public Connection(Robot robot) {
        this.r2d2 = robot;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!connectionStatus) {
            throw new RobotConnectionException("Connection error!");
        }
        r2d2.setxCoord(x);
        r2d2.setyCoord(y);
    }

    @Override
    public void close() {
        connectionStatus = false;
        r2d2 = null;
    }
}
