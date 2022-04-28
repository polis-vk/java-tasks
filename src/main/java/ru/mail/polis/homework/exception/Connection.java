package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private Robot robot;
    private boolean connected;

    public Connection(int id) {
        robot = new Robot(id);
        connected = true;
    }

    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!connected) {
            throw new RobotConnectionException();
        }
        int[] coordinates = robot.getCoordinates();
        robot.setCoordinates(coordinates[0] + x, coordinates[1] + y);
    }

    public boolean isConnected() {
        robot = null;
        return connected;
    }

    public void close() {
        connected = false;
    }
}
