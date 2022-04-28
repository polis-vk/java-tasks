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
    private boolean isConnected;
    private int powerValue;

    public Robot(int x, int y, int id) {
        point = new PairPoint();
        point.x = x;
        point.y = y;
        this.id = id;
        powerValue = 100;
        isConnected = false;
    }

    public void setPoint(int x, int y) throws RobotPowerException {
        int estimatedBatteryConsumption = Math.abs(point.x - x) + Math.abs(point.y - y);
        if (estimatedBatteryConsumption <= powerValue) {
            throw new RobotPowerException("Moving is not possible. The robot's battery charge is not enough for this");
        }
        powerValue -= estimatedBatteryConsumption;
        point.x = x;
        point.y = y;
    }

    public int getX() {
        return point.x;
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

    public boolean getRobotStatusConnection() {
        return isConnected;
    }

    public void connect() {
        isConnected = true;
    }

    public void disconnect() {
        isConnected = false;
    }

    private static class PairPoint {
        private int x;
        private int y;
    }
}
