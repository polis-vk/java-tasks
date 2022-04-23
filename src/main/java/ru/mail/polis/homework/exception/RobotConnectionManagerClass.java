package ru.mail.polis.homework.exception;

public class RobotConnectionManagerClass implements RobotConnectionManager{
    @Override
    public RobotConnection getConnection(int robotId) throws RobotConnectException {
        Robot robot = RobotRemoteControl.robotList.get(robotId);
        if(robot == null){
            throw new RobotConnectException("The attempt to connect robot failed");
        }
        return new RobotConnectionClass(robot);
    }
}
