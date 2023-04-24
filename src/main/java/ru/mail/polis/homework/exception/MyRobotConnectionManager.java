package ru.mail.polis.homework.exception;
import java.util.Map;
public class MyRobotConnectionManager implements RobotConnectionManager{
    private final Map<Integer, Robot> robotMap;

    public MyRobotConnectionManager() {
        robotMap = null;
    }
    @Override
    public RobotConnection getConnection(int robotId) throws ConnectException {
        Robot robot = robotMap.get(robotId);
        if (robot == null) {
            throw new ConnectException();
        }
        return new MyRobotConnection(robot);
    }

    @Override
    public void addRobot(Robot robot) {
        robotMap.put(robot.getId(), robot);
    }
}
