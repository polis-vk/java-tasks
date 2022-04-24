package ru.mail.polis.homework.exception;

public enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT;

    public static Direction getLeftDirection(Direction direction) {
        switch (direction) {
            case UP:
                direction = Direction.LEFT;
                break;
            case DOWN:
                direction = Direction.RIGHT;
                break;
            case LEFT:
                direction = Direction.DOWN;
                break;
            case RIGHT:
                direction = Direction.UP;
            default:
        }
        return direction;
    }

    public static Direction getRightDirection(Direction direction) {
        switch (direction) {
            case UP:
                direction = Direction.RIGHT;
                break;
            case DOWN:
                direction = Direction.LEFT;
                break;
            case LEFT:
                direction = Direction.UP;
                break;
            case RIGHT:
                direction = Direction.DOWN;
            default:
        }
        return direction;
    }
}
