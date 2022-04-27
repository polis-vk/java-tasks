package ru.mail.polis.homework.exception;

import java.util.List;
import java.util.HashMap;

public class ConnectionManager implements RobotConnectionManager {
    private final HashMap<Integer, Robot> robots;

    public ConnectionManager(List<Robot> listOfRobots) {
        robots = new HashMap<>();
        for (Robot robot : listOfRobots) {
            robots.put(robot.getId(), robot);
        }
    }

    @Override
    public RobotConnection getConnection(int robotId) throws ConnectionFailedException {
        if (!robots.containsKey(robotId)) {
            throw new ConnectionFailedException("No such robotID");
        }
        Robot robot = robots.get(robotId);
        if (robot.isConnected()) {
            throw new ConnectionFailedException("Connection already exist");
        }
        return new Connection(robot);
    }
}
