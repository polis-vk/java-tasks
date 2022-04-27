package ru.mail.polis.homework.exception;

public class Connection implements RobotConnection{
    private boolean isConnected;
    private final Robot robot;

    Connection(int id) {
        this.robot = new Robot(id);
        this.isConnected = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws ConnectionException {

        if(!isConnected) {
            throw new ConnectionException("Ошибка подключения");
        }
        robot.setCoordinate_x(x);
        robot.setCoordinate_y(y);
    }


    @Override
    public void close() {
        this.isConnected = false;
    }

}
