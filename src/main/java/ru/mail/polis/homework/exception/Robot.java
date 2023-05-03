package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */
public class Robot {

    private final int robotId;
    private boolean isServiceable = true;
    private int x = 0;
    private int y = 0;

    public Robot(int robotId) {
        this.robotId = robotId;
    }

    public Robot(int robotId, int x, int y) {
        this.robotId = robotId;
        this.x = x;
        this.y = y;
    }

    public int getRobotId() {
        return robotId;
    }

    public boolean isServiceable() {
        return isServiceable;
    }

    public void setServiceable(boolean serviceability) {
        isServiceable = serviceability;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
