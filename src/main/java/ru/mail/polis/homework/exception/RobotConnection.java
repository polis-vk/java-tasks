package ru.mail.polis.homework.exception;

public class RobotConnection implements Connection{
    private final Robot robot;
    private boolean isConnected;

    public RobotConnection(Robot robot) {
        this.robot = robot;
        isConnected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!isConnected){
            throw new RobotConnectionException("Failed move robot because of no connection");
        }
        robot.move(x,y);
    }

    @Override
    public void close() {
        isConnected = false;
    }
}
