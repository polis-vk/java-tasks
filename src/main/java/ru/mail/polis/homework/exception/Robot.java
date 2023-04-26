package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 *<p>
 * 2 тугрика
 */
public class Robot {
    private final int id;
    private int x;
    private int y;
    private boolean isConnected;
    private boolean isHappy;

    public Robot(int id, int x, int y) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.isConnected = false;
        this.isHappy = false;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public int getID() {
        return id;
    }
    public boolean isConnected() {
        return isConnected;
    }
    public boolean isHappy() {
        return isHappy;
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
        this.isHappy = true;
    }
}
