package ru.mail.polis.homework.exception;

import java.util.HashMap;

public class ConnectionManager implements RobotConnectionManager {

    private HashMap<Integer, Robot> robotsMap;
    private Robot robot1;
    private Robot robot2;
    private Robot robot3;
    private int count = 0;


    public void setRobots() {
        robotsMap.put(1, robot1);
        robotsMap.put(2, robot2);
        robotsMap.put(3, robot3);
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectionException {
        if (count == 0) {
            setRobots();
        }
        if (robotsMap.get(robotId) == null) {
            throw new RobotConnectionException("Connection error!");
        }
        Connection robotConnection = new Connection(robotsMap.get(robotId));
        if (!robotConnection.getConnectionStatus()) {
            throw new RobotConnectionException("Connection error!");
        }
        count++;
        return robotConnection;
    }
}
