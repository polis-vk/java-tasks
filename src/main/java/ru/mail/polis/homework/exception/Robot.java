package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */
public class Robot {

    private static int robotCount = 0;
    private final int robotId;
    private int x;
    private int y;

    public Robot(int x, int y) {
        this.x = x;
        this.y = y;
        this.robotId = ++robotCount;
    }

    public int getRobotId() {
        return robotId;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
