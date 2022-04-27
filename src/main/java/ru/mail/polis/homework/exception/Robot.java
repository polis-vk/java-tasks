package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 *
 * 2 тугрика
 */
public class Robot {
    private final int id;
    private int coordinate_x;
    private int coordinate_y;

    public  Robot(int id) {
        this.id = id;
    }

    public int getCoordinate_x() {
        return coordinate_x;
    }

    protected void setCoordinate_x(int coordinate_x) {
        this.coordinate_x = coordinate_x;
    }

    public int getCoordinate_y() {
        return coordinate_y;
    }

    protected void setCoordinate_y(int coordinate_y) {
        this.coordinate_y = coordinate_y;
    }


}
