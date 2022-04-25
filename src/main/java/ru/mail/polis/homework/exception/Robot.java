package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 *
 * 2 тугрика
 */
public class Robot {
    private final int id;
    private int x;
    private int y;
    private boolean isConnected;

    public Robot(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isConnected = false;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
