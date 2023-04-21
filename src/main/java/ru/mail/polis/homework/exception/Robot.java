package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */
public class Robot {
    double x;
    double y;
    final int id;

    public Robot(double x, double y, int id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }
}
