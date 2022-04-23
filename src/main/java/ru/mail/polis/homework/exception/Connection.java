package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private final Robot robot;
    private boolean isConnected;

    public Connection(int id) {
        robot = new Robot(id);
        isConnected = true;
    }

    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (isConnected) {
            int[] coordinates = robot.getCoordinates();
            robot.setCoordinates(coordinates[0] + x, coordinates[1] + y);
        } else {
            throw new RobotConnectionException();
        }
    }

    public boolean isOpen() {
        return isConnected;
    }

    public void close() {
        this.isConnected = false;
    }
}
