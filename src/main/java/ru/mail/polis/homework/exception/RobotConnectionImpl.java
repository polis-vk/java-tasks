package ru.mail.polis.homework.exception;

public class RobotConnectionImpl implements RobotConnection {
    private final Robot robot;
    private boolean opened;

    public RobotConnectionImpl(Robot robot) {
        this.robot = robot;
        opened = true;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!opened) {
            throw new RobotConnectionException("Failed move robot because of no connection");
        }
        robot.move(x, y);
    }

    @Override
    public void makeRoar() throws RobotConnectionException {
        if (!opened) {
            throw new RobotConnectionException("Failed make roar because of no connection");
        }
        robot.makeRoar();
    }

    @Override
    public void pickMushroom() throws RobotConnectionException {
        if (!opened) {
            throw new RobotConnectionException("Failed pick mushroom because of no connection");
        }
        robot.pickMushroom();
    }

    @Override
    public void throwMushroom() throws RobotConnectionException {
        if (!opened) {
            throw new RobotConnectionException("Failed throw mushroom because of no connection");
        }
        robot.throwMushroom();
    }

    @Override
    public void close() {
        opened = false;
    }
}
