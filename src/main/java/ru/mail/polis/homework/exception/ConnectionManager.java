package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {
     private final Map<Integer, Robot> robotMap;
    ConnectionManager(Map<Integer, Robot> robotMap) {
        this.robotMap = new HashMap<>(robotMap);
    }

    @Override
    public RobotConnection getConnection(int id) throws RobotConnectionException {
        Robot robot = robotMap.get(id);
        if (robot == null) {
            throw new RobotConnectionException("Cant find robot with id: " + id);
        }
        return new RobotStream(robot);
    }

    //Добавляем нового робота в менеджер соединений;
    public void addNewRobot(int robotId, Robot robot) {
        robotMap.putIfAbsent(robotId, robot);
    }

}
