package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */
public class Robot {
    enum RobotMoveDirections {
        NORTH {
            public void move(Robot robot) {
                robot.y++;
            }
        },
        SOUTH {
            public void move(Robot robot) {
                robot.y--;
            }
        },
        WEST {
            public void move(Robot robot) {
                robot.x--;
            }
        },
        EAST {
            public void move(Robot robot) {
                robot.x++;
            }
        };

        public abstract void move(Robot robot);
    }

    private int x;
    private int y;
    private boolean broken;

    private final int id;

    public Robot(int x, int y, int id) {
        this.x = x;
        this.y = y;
        this.broken = false;
        this.id = id;
    }

    public void move(RobotMoveDirections direction) throws RobotException {
        if (broken) {
            throw new RobotException("Can't move: robot is broken!");
        }
        direction.move(this);
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

    public boolean isBroken() {
        return broken;
    }

    public int getId() {
        return id;
    }
}
