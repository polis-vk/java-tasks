package ru.mail.polis.homework.exception;

public class RobotManager implements RobotConnectionManager {
    @Override
    public RobotConnection getConnection(int robotId) throws ConnectionException, NoEnergyException {
        Robot robot = new Robot(10, 0.5, robotId);
        Connection connection = new Connection(robot, robot.connectionStatus());
        if (connection.getID() == robotId) {
            return connection;
        } else {
            throw new ConnectionException("Connection error!");
        }
    }
}
