package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */
public class Robot {
    private int xCoord;
    private int yCoord;
    private int id;

    public Robot(int xCoord, int yCoord, int id) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.id = id;
    }

    public Robot(int id) {
        this.id = id;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
