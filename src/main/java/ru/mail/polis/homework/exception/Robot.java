package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 *
 * 2 тугрика
 */
public class Robot {

    private final int robotId;
    private int x;
    private int y;
    Direction direction;

    public Robot(int robotId, Direction direction) {
        this.robotId = robotId;
        this.direction = Direction.UP;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getRobotId() {
        return robotId;
    }

    public void turnLeft() {
        direction = Direction.getLeftDirection(direction);
    }

    public void turnRight() {
        direction = Direction.getRightDirection(direction);
    }

    public void stepForward() {
        switch (direction) {
            case RIGHT:
                x++;
                break;
            case LEFT:
                x--;
                break;
            case UP:
                y++;
                break;
            case DOWN:
                y--;
                break;
            default:
        }
    }
}
