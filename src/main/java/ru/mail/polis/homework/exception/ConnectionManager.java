package ru.mail.polis.homework.exception;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {

    private final Map<Integer, Robot> robots = new HashMap<>();

    public ConnectionManager() {
        // add default robots
        robots.putIfAbsent(123, new Robot(123, 0, 0));
        robots.putIfAbsent(234, new Robot(234, 25, 12));
        robots.putIfAbsent(456, new Robot(456, 15, 15));
    }

    public ConnectionManager(List<Robot> robots) {
        for (Robot robot : robots) {
            this.robots.putIfAbsent(robot.getId(), robot);
        }
    }

    public void addRobot(Robot robot) {
        robots.putIfAbsent(robot.getId(), robot);
    }

    public boolean removeRobot(int id) {
        return robots.remove(id) != null;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws ConnectionException {
        Robot robot = robots.get(robotId);
        if (robot == null) {
            throw new ConnectionException("No such robot on the field");
        }
        return new Connection(robot);
    }
}
