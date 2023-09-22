package ru.mail.polis.homework.exception;

public class RobotRemoteConnection implements RobotConnection{
    private boolean isConnected = false;
    private static final int START = 10, END = 15;
    private int ATTEMPTS = 2;
    private Robot robot;

    RobotRemoteConnection(Robot robot)
    {
        this.robot = robot;
    }


    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        data:
        while(ATTEMPTS > 0)
        {
            if((Math.random()*(END-START)+START) % 2 == 0)  {
                isConnected = true;
                break data;
            }
        }
        if(!isConnected) throw new RobotConnectionException();
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() {
    }
}
