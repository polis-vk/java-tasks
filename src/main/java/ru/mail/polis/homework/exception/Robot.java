package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */
public class Robot {
    private Coordinates<Integer> coordinates;
    private final int id;
    private boolean isConnected;

    public Robot(int id) {
        this.id = id;
        this.isConnected = false;
    }

    public void setCoordinates(int x, int y) {
        this.coordinates = new Coordinates<>(x, y);
    }

    public Coordinates<Integer> getCoordinates() {
        return coordinates;
    }

    public int getId() {
        return id;
    }

    public void setConnection(boolean isConnected) {
        this.isConnected = isConnected;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public static class Coordinates<T> {
        private final T x;
        private final T y;

        public Coordinates(T x, T y) {
            this.x = x;
            this.y = y;
        }

        public T getX() {
            return x;
        }

        public T getY() {
            return y;
        }
    }
}
