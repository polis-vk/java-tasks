package ru.mail.polis.homework.exception;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Direction getLeftDirection(Direction direction) {
        switch (direction) {
            case UP:
                return Direction.LEFT;
            case DOWN:
                return Direction.RIGHT;
            case LEFT:
                return Direction.DOWN;
            case RIGHT:
                return Direction.UP;
            default:
                return direction;
        }
    }

    public static Direction getRightDirection(Direction direction) {
        switch (direction) {
            case UP:
                return Direction.RIGHT;
            case DOWN:
                return Direction.LEFT;
            case LEFT:
                return Direction.UP;
            case RIGHT:
                return Direction.DOWN;
            default:
                return direction;
        }
    }
}
