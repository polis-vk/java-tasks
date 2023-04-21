package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */
public class Robot {
    private int x;
    private int y;

    public Robot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean moveTo(int toX, int toY) {  // делает шаг к цели
        if (x > toX) {
            x--;
        } else {
            x++;
        }

        if (y > toY) {
            y--;
        } else {
            y++;
        }
        System.out.println("Moved to (" + x + ", " + y + ") position.");
        return x == toX && y == toY;
    }
}
