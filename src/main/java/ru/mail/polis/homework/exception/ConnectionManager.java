package ru.mail.polis.homework.exception;

import java.util.Map;

public class ConnectionManager implements RobotConnectionManager {

    private final Map<Integer, Robot> field = Map.of(
            123, new Robot(123, 0, 0),
            234, new Robot(234, 25, 12),
            456, new Robot(456, 15, 15)
    );

    @Override
    public RobotConnection getConnection(int robotId) throws ConnectionException {
        Robot robot = field.get(robotId);
        if (robot == null) {
            throw new ConnectionException("No such robot on the field");
        }
        if (robot.isConnected()) {
            throw new ConnectionException("Robot already connected");
        }
        return new Connection(robot);
    }
}
