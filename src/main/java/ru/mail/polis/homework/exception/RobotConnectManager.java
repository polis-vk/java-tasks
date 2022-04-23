package ru.mail.polis.homework.exception;

public class RobotConnectManager implements RobotConnectionManager {
    private boolean connection;
    private WorkWithRobot existingRobots;

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Connection robotConnection = new Connection(existingRobots.getRobots().get(robotId), connection);
        if (robotConnection.getConnectionStatus()) {
            return robotConnection;
        } else {
            throw new RobotConnectionException("Connection error!");
        }
    }
}
