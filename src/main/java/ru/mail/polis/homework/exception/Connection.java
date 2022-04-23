package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private final Robot robot;
    private int robotId;

    public Connection(int robotId) {
        this.robotId = robotId;
        robot = RobotsInTheField.realRobots.get(robotId);
    }

    public boolean connectionStatus() throws NoEnergyException {
        if (robot != null && robotId == RobotsInTheField.realRobots.get(robotId).getID() && robot.connection()) {
            robot.changeStatus(true);
            return true;
        }
        return false;
    }

    @Override
    public void moveRobotTo(double x, double y) {
        robot.setX(x);
        robot.setY(y);
    }

    @Override
    public void close() {

    }

}

