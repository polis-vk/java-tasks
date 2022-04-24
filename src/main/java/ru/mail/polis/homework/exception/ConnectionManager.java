package ru.mail.polis.homework.exception;


import java.util.HashMap;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {
    public final static Map<Integer, Robot> ROBOTS = new HashMap<>();
    private final RobotRemoteControl robotRemoteControl = new RobotRemoteControl();

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        Robot robot = robotRemoteControl.getRobot(robotId);
        if (robot == null) {
            throw new RobotConnectionException("Попытка подключения робота завершилась неудачей");
        }
        return new Connection(robot);
    }

    public Robot getRobot(int id) {
        return ROBOTS.get(id);
    }
}
