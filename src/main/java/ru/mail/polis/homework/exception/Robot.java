package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 *
 * 2 тугрика
 */
public class Robot {
    private final int modelNumber;
    private int x;
    private int y;

    Robot(int modelNumber, int x, int y) {
        this.modelNumber = modelNumber;
        this.x = x;
        this.y = y;
    }

    public int getModelNumber() {
        return modelNumber;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setCoordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
