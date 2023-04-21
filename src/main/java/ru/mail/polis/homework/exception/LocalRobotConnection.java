package ru.mail.polis.homework.exception;

public class LocalRobotConnection implements RobotConnection {
    private int connectionResources = 5;
    private final Robot robot;

    public LocalRobotConnection(Robot connectedRobot) {
        this.robot = connectedRobot;
    }

    @Override
    public void moveRobotTo(int x, int y) throws Exception {
        while (connectionResources > 0) {
            connectionResources--;
            if (robot.moveTo(x, y)) {
                System.out.println("Robot reached goal");
                return;
            }
        }
        System.out.println("Connection lost");
        throw new Exception();
    }

    @Override
    public void close() {
        System.out.println("Closing connection");
    }
}

