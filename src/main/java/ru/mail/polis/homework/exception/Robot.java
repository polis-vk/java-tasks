package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */
public class Robot {
    private int robotId;
    private int x;
    private int y;
    private boolean connected = false;

    public boolean isConnected() {
        return connected;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRobotId() {
        return robotId;
    }

    public void connect() {
        connected = true;
    }

    public void disConnect() {
        connected = false;
    }

    public void move(int x, int y) throws ConnectionException {
        if (connected) {
            this.x = x;
            this.y = y;
        } else {
            throw new ConnectionException("There is no one to control the robot");
        }
    }
}
