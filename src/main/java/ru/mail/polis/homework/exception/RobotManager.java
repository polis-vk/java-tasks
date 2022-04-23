package ru.mail.polis.homework.exception;

public class RobotManager implements RobotConnectionManager {
    @Override
    public RobotConnection getConnection(int robotId) throws ConnectionException, NoEnergyException {
        Connection connection = new Connection(robotId);
        if (connection.connectionStatus()) {
            return connection;
        } else {
            throw new ConnectionException("Connection error!");
        }
    }
}
