package ru.mail.polis.homework.exception;

public class ConnectionManager implements RobotConnectionManager {

    private final Robot[] robotsPool;

    public ConnectionManager(int robotsNumber) {
        robotsPool = new Robot[robotsNumber];
        for (int i = 0; i < robotsNumber; i++) {
            robotsPool[i] = new Robot(0, 0, i + 1);
        }
    }

    @Override
    public RobotConnection getConnection(int robotId) throws RobotEstablishConnectionException {
        if (!searchRobotById(robotId)) {
            throw new RobotEstablishConnectionException("Connection to the robot was not successful");
        }
        return new Connection(robotsPool[robotId]);
    }

    private boolean searchRobotById(int robotId) {
        for (Robot robot : robotsPool) {
            if (robot.getId() == robotId) {
                return true;
            }
        }
        return false;
    }
}
