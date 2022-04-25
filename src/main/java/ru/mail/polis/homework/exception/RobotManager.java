package ru.mail.polis.homework.exception;

public class RobotManager implements RobotConnectionManager {
    @Override
    public RobotConnection getConnection(int robotId) throws ConnectionException, NoEnergyException {
        Robot robot = RobotsInTheField.realRobots.get(robotId);
        if (robot != null && robotId == RobotsInTheField.realRobots.get(robotId).getID() && robot.connection()) {
            return new Connection(robot);
        } else {
            throw new ConnectionException("Connection error!");
        }
    }
}
