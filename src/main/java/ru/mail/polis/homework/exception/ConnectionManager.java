package ru.mail.polis.homework.exception;

public class ConnectionManager implements RobotConnectionManager {

    @Override
    public RobotConnection getConnection(int robotId) throws ConnectionException {
        Connection connection = new Connection();
        connection.connectionToRobot(new Robot(robotId));
        return connection;
    }
}
