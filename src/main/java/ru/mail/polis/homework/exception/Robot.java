package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 *
 * 2 тугрика
 */
public class Robot {

    private int x;
    private int y;
    private final int robotId;

    public Robot(int id) {
        robotId = id;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getRobotId() {
        return robotId;
    }
}
