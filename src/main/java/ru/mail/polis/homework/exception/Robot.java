package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */
public class Robot {
    private final PairPoint point;
    private final int id;
    private int powerValue;

    public Robot(int x, int y, int id) {
        point = new PairPoint();
        point.x = x;
        point.y = y;
        this.id = id;
        powerValue = 100;
    }

    public int getX() {
        return point.x;
    }

    public void setPoint(int x, int y) throws RobotPowerException {
        if (!checkBatteryCapabilities(x, y)) {
            throw new RobotPowerException("Moving is not possible. The robot's battery charge is not enough for this");
        }
        powerValue -= Math.abs(point.x - x) + Math.abs(point.y - y);
        point.x = x;
        point.y = y;
    }

    public int getY() {
        return point.y;
    }

    public int getId() {
        return id;
    }

    public int getPowerValue() {
        return powerValue;
    }

    private boolean checkBatteryCapabilities(int x, int y) {
        return (Math.abs(point.x - x) + Math.abs(point.y - y)) <= powerValue;
    }

    private static class PairPoint {
        private int x;
        private int y;
    }
}
