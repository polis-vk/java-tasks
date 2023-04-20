package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */
public class Robot {

    private final int ID;
    private int x;
    private int y;
    private boolean isConnected;
    private boolean isDepressed;

    public Robot(int id, int x, int y) {
        ID = id;
        this.x = x;
        this.y = y;
        this.isConnected = false;
        this.isDepressed = true;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    public int getID() {
        return ID;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public boolean isDepressed() {
        return isDepressed;
    }

    public void connect() {
        isConnected = true;
    }

    public void disconnect() {
        isConnected = false;
    }

    public void move(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void dance() {
        this.isDepressed = false;
    }
}