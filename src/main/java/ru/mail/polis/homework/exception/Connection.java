package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private final Robot robot;
    private boolean isConnected;

    public Connection(int id) {
        robot = new Robot(id);
        isConnected = true;
    }

    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!isConnected) {
            throw new RobotConnectionException();
        }
        int[] coordinates = robot.getCoordinates();
        robot.setCoordinates(coordinates[0] + x, coordinates[1] + y);
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void close() {
        isConnected = false;
    }
}
