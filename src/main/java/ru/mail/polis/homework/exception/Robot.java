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
    private int id;
    private Color color;

    public Robot(int x, int y, int id, Color color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    public Robot(int x, int y, int id) {
        this(x, y, id,  Color.BLACK);
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

    public int getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
