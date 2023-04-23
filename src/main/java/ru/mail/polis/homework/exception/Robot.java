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
    boolean broken;

    public Robot() {
        this.x = 0;
        this.y = 0;
        this.broken = false;
    }

    public void move(String direction) throws RobotException {
        if (broken) {
            throw new RobotException("Can't move: robot is broken!");
        }
        switch (direction) {
            case "south":
                y--;
                break;
            case "north":
                y++;
                break;
            case "east":
                x++;
                break;
            case "west":
                x--;
                break;
        }
        int chance = (int) (Math.random() * 10);
        if (chance == 1) {
            broken = true;
        }
    }

    public void trySelfRepair() {
        int chance = (int) (Math.random() * 2);
        if (chance == 1) {
            broken = false;
        }
    }


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
