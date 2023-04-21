package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {
    final private Map<Integer, Robot> robotMap;
    final private Map<Integer, RobotConnection> connectionMap;

    ConnectionManager(Map<Integer, Robot> robotMap) {
        this.robotMap = robotMap;
        connectionMap = new HashMap<>();
    }

    @Override
    public RobotConnection getConnection(int id) {
        if (!robotMap.containsKey(id)) {
            throw new RuntimeException("RobotNotFound");
        }
        connectionMap.putIfAbsent(id, new RobotStream(robotMap.get(id)));
        return connectionMap.get(id);
    }

    //Добавляем нового робота в менеджер соединений;
    public void addNewRobot(int robotId, Robot robot) {
        robotMap.putIfAbsent(robotId, robot);
    }

}
