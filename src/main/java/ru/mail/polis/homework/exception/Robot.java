package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */
public class Robot {
    private final int robotId;
    private int x;
    private int y;
    private boolean hasMushroom;

    public Robot(int robotId, int x, int y) {
        this.robotId = robotId;
        this.x = x;
        this.y = y;
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

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void makeRoar() {
        System.out.println("ROOOAAAARRR!!!");
    }

    public void pickMushroom() {
        this.hasMushroom = true;
    }

    public void throwMushroom() {
        this.hasMushroom = false;
    }
}
