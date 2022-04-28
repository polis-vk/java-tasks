package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection{

    private Robot robot;
    private boolean isConnected;

    public Connection(Robot robot) {
        this.robot = robot;
        isConnected = true;
    }


    public boolean isConnected(){
        return isConnected;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!isConnected()){
            throw new RobotConnectionException("Connection failed :(");
        }
        robot.setX(x);
        robot.setY(y);
        int newColor = robot.getColor().getColorCode() + 1;
        if (newColor > 7){
            newColor = 0;
        }
        robot.setColor(Color.getColor(newColor));
    }

    @Override
    public void close() {
        isConnected = false;
    }
}
