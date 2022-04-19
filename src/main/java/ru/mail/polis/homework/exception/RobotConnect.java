package ru.mail.polis.homework.exception;

public class RobotConnect implements RobotConnection {
    private Robot R2D2;
    private boolean connection;

    public boolean getConnection() {
        return connection;
    }

    public RobotConnect(Robot robot, boolean connection) {
        this.R2D2 = robot;
        this.connection = connection;
    }

    @Override
    public void moveRobotTo(int x, int y) throws Exception {
        if (connection) {
            R2D2.setxCoord(x);
            R2D2.setyCoord(y);
        } else {
            throw new Exception("Connection error!");
        }
    }

    @Override
    public void close() {
        connection = false;
        R2D2 = null;
    }

    public RobotConnect getConnection(Robot robot) {
        return new RobotConnect(robot, connection);
    }
}
