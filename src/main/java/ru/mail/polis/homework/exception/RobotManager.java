package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.List;

public class RobotManager implements RobotConnectionManager {

    public final HashMap<Integer, Robot> realRobots = new HashMap<>();

    public RobotManager(List<Robot> robots) {
        for (Robot robot : robots) {
            realRobots.put(robot.getID(), robot);
        }
    }

    @Override
    public RobotConnection getConnection(int robotId) throws ConnectionException, NoEnergyException {
        Robot robot = realRobots.get(robotId);
        if (robot != null && robot.connection()) {
            return new Connection(robot);
        } else {
            throw new ConnectionException("Connection error!");
        }
    }
}
