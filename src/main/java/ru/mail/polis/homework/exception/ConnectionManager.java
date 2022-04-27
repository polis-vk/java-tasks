package ru.mail.polis.homework.exception;

import java.util.Map;

public class ConnectionManager implements RobotConnectionManager{
    private final Map<Integer, Robot> robotMap;

    protected ConnectionManager(Map<Integer, Robot> robotMap) {
      this.robotMap = robotMap;
  }

    @Override
    public RobotConnection getConnection(int robotId) throws ConnectionException {
        Robot robot = robotMap.get(robotId);
        if (robot == null) {
            throw new ConnectionException("Error. This id not exist");
        }

        return new Connection(robotId);
    }
}
