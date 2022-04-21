package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 *
 * 2 тугрика
 */
public class Robot {

    int x = 0;
    int y = 0;
    private final int robotId;
    Direction direction = Direction.UP;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Robot(int robotId) {
        this.robotId = robotId;
    }

    public Direction getDirection() {
        return direction;
    }

    public void turnLeft() {
        switch (getDirection()) {
            default:
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
        }
    }

    public void turnRight() {
        switch (getDirection()) {
            default:
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
        }
    }

    public void stepForward() {
        switch (getDirection()) {
            default:
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
        }
    }


    public void moveRobotTo(Robot robot, int toX, int toY) {

        if (robot.getX() < toX) {
            while (robot.getDirection() != Direction.RIGHT) robot.turnRight();
            while (robot.getX() < toX) robot.stepForward();
        }

        if (robot.getX() > toX) {
            while (robot.getDirection() != Direction.LEFT) robot.turnRight();
            while (robot.getX() > toX) robot.stepForward();
        }

        if (robot.getY() < toY) {
            while (robot.getDirection() != Direction.UP) robot.turnLeft();
            while (robot.getY() < toY) robot.stepForward();
        }

        if (robot.getY() > toY) {
            while (robot.getDirection() != Direction.DOWN) robot.turnLeft();
            while (robot.getY() > toY) robot.stepForward();
        }
    }
}
