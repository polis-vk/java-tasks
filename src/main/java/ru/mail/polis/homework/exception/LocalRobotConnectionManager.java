package ru.mail.polis.homework.exception;

import java.util.Map;

public class LocalRobotConnectionManager implements RobotConnectionManager {
    private final Map<Integer, Robot> robots;

    public LocalRobotConnectionManager(Map<Integer, Robot> robots) {
        this.robots = robots;
    }

    @Override
    public RobotConnection getConnection(int robotId) throws Exception {
        if (robots.containsKey(robotId)) {
            return new LocalRobotConnection(robots.get(robotId));
        } else {
            System.out.println("No such robot available");
            throw new Exception();
        }
    }
}
