package ru.mail.polis.homework.exception;


public class ConnectionManager implements RobotConnectionManager {
    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = new RobotRemoteControl().getRobot(robotId);
        if (robot == null) {
            throw new RobotConnectionException("Попытка подключения робота завершилась неудачей");
        }
        return new Connection(robot);
    }
}
