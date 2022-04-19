package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */
public class Robot {
    private int x;
    private int y;
    private final int robotId;
    private boolean isConnected;

    public Robot(int robotId) {
        this.robotId = robotId;
        isConnected = false;
    }

    public Robot(int robotId, int toX, int toY) {
        this(robotId);
        x = toX;
        y = toY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }
}
