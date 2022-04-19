package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private Robot robot;
    private boolean connection;

    public Connection(Robot robot, boolean connectionStatus) {
        this.robot = robot;
        this.connection = connectionStatus;
    }

    public boolean getConnectionStatus() {
        return connection;
    }

    public int getID() {
        return this.robot.getId();
    }

    @Override
    public void moveRobotTo(double x, double y) throws ConnectionException, NoEnergyException {
        if (getConnectionStatus() & robot != null) {
            robot.setX(x);
            robot.setY(y);
        } else {
            throw new ConnectionException("Это не ваш робот!");
        }
    }

    @Override
    public void close() {
        connection = false;
        robot = null;
    }
}
