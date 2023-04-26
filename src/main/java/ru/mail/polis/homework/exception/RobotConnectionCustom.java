package ru.mail.polis.homework.exception;

class RobotConnectionCustom implements RobotConnection {
    private final Robot robot;
    private boolean connection;

    protected RobotConnectionCustom(Robot robot) {
        this.robot = robot;
    }

    @Override
    public void moveRobotTo(int x, int y) throws RobotConnectionException {
        if (!connection) {
            throw new RobotConnectionException("Error connection to Robot with id = " + robot.getId(), robot.getId());
        }
        robot.moveTo(x, y);
    }

    @Override
    public boolean isConnectionAlive() {
        return this.connection;
    }

    @Override
    public void close() {
        connection = false;
    }
}
