package ru.mail.polis.homework.exception;

public class RobotConnectManager implements RobotConnectionManager {
    private boolean connection;

    @Override
    public RobotConnection getConnection(int robotId) throws Exception {
        Robot C3PIO = new Robot(robotId);
        RobotConnect RobotConnection = new RobotConnect(C3PIO, connection);
        if (RobotConnection.getConnection()) {
            return RobotConnection;
        } else {
            throw new Exception("Connection error!");
        }
    }
}
