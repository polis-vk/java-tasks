package ru.mail.polis.homework.exception.impl;

import ru.mail.polis.homework.exception.Robot;
import ru.mail.polis.homework.exception.RobotConnection;
import ru.mail.polis.homework.exception.RobotConnectionManager;

import java.util.HashMap;
import java.util.Map;

public class RobotConnectionManagerImpl implements RobotConnectionManager {

    private final Map<Integer, Robot> robots;

    public RobotConnectionManagerImpl(Map<Integer, Robot> robots) {
        this.robots = robots;
    }

    @Override
    public RobotConnection getConnection(int robotId) {
        if (!robots.containsKey(robotId)) {
            throw new IllegalArgumentException("No such robot " + robotId);
        }
        return new RobotConnectionImpl(robots.get(robotId));
    }

}
