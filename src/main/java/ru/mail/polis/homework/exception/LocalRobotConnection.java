package ru.mail.polis.homework.exception;

public class LocalRobotConnection implements RobotConnection {
    private int connectionResources;
    private boolean isConnected;
    private final Robot robot;

    public LocalRobotConnection(Robot connectedRobot, int connectionResources) {
        this.robot = connectedRobot;
        this.connectionResources = connectionResources;
        isConnected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws ConnectionException {
        while (isConnected && connectionResources > 0) {
            connectionResources--;
            if (moveTo(robot, x, y)) {
                System.out.println("Robot reached goal");
                return;
            }
        }
        throw new ConnectionException("Connection lost");
    }

    public boolean moveTo(Robot robot, int toX, int toY) {  // делает шаг к цели
        int currentXPos = robot.getX();
        int currentYPos = robot.getY();

        if (currentXPos > toX) {
            robot.setX(--currentXPos);
        } else {
            robot.setX(++currentXPos);
        }

        if (currentYPos > toY) {
            robot.setY(--currentYPos);
        } else {
            robot.setY(++currentYPos);
        }

        System.out.println("Moved to (" + currentXPos + ", " + currentYPos + ") position.");
        return currentXPos == toX && currentYPos == toY;
    }

    @Override
    public void close() {
        isConnected = false;
        System.out.println("Closing connection");
    }
}

