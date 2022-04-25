package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection {
    private Robot robot;
    private final int robotId;

    public Connection(Robot robot) {
        this.robot = robot;
        robotId = robot.getID();
    }

    public boolean connectionStatus() throws NoEnergyException {
        if (robotId == RobotsInTheField.realRobots.get(robotId).getID() && robot.connection()) {
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
        robot.changeStatus(false);
        robot = null;
    }

}

