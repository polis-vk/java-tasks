package ru.mail.polis.homework.exception;

import java.util.NoSuchElementException;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */
public class Robot {
    private final int id;
    private int x;
    private int y;
    private boolean isConnected;
    private boolean isTurnedOn;

    public Robot(int id) {
        this.id = id;
    }

    public Robot(int id, int x, int y, boolean isTurnedOn, boolean isConnected) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isTurnedOn = isTurnedOn;
        this.isConnected = isConnected;
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

    public boolean isConnected() {
        return isConnected;
    }
    public boolean isTurnedOn() {
        return isTurnedOn;
    }

    public void setStatusConnection(boolean status) {
        isConnected = status;
    }

    public void setStatusWork(boolean status) {
        isTurnedOn = status;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
