package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */
public class Robot {
    private double x;
    private double y;
    private static int counterRobots = 1;
    private final int id;

    public Robot() {
        id = counterRobots;
        counterRobots++;
    }

    public Robot(double x, double y) {
        this.x = x;
        this.y = y;
        id = counterRobots;
        counterRobots++;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

}
