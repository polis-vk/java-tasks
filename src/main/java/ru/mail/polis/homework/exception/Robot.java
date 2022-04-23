package ru.mail.polis.homework.exception;

/**
 * Реализовать простого робота.
 * У него должны быть координаты, работа с ними и все остальное на ваше усмотрение
 * <p>
 * 2 тугрика
 */

// Математические расчеты отнимают 5 единиц энергии.
// Будем считать, что отправка и установка координат не требует значительной электроэнергии.
// Прыжок отнимает много энергии согласно КПД (коэффициенту).
// Передвижения от точки до точки отнимают много энергии согласно КПД.
// Все точки представлены другими роботами со своими координатами.
// Когда у него заканчивается электричество - он автоматически начинает подзарядку.
// Если заканчивается ископаемое топливо - робот останавливается.
// Если энергия заканчивается - связь с роботом обрывается и прилетает исключение

public class Robot {
    private double x;
    private double y;
    private final int id;
    private boolean connection = false;

    private double electricalCapacity = 100;
    private double mineralFuel;

    private final double EFFICIENCY;

    public Robot(double mineralFuel, double efficiency, int id) {
        this.id = id;
        this.mineralFuel = mineralFuel;
        EFFICIENCY = efficiency;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double point) {
        x = point;
    }

    public void setY(double point) {
        y = point;
    }

    public int getID() {
        return id;
    }

    public boolean connection() throws NoEnergyException {
        electricalCapacity -= 5 + 5 * (1 - EFFICIENCY);
        if (electricalCapacity < 1) {
            generateElectricity();
        }
        return connection;
    }

    public void changeStatus(boolean status) throws NoEnergyException {
        electricalCapacity -= 5 + 5 * (1 - EFFICIENCY);
        if (electricalCapacity < 1) {
            generateElectricity();
        }
        connection = status;
    }

    public double distance(Robot robot) throws NoEnergyException {
        electricalCapacity -= 5 + 5 * (1 - EFFICIENCY);
        if (electricalCapacity < 1) {
            generateElectricity();
        }
        return Math.sqrt((x - robot.getX()) * (x - robot.getX()) + (y - robot.getY()) * (y - robot.getY()));
    }

    public double time(Robot robot, double speed) throws NoEnergyException {
        electricalCapacity -= 5 + 5 * (1 - EFFICIENCY);
        if (electricalCapacity < 1) {
            generateElectricity();
        }
        return distance(robot) / speed;
    }

    public double speed(Robot robot, int time) throws NoEnergyException {
        electricalCapacity -= 5 + 5 * (1 - EFFICIENCY);
        if (electricalCapacity < 1) {
            generateElectricity();
        }
        return distance(robot) / time;
    }

    public double height(double time) throws NoEnergyException {
        electricalCapacity -= 20 + 20 * (1 - EFFICIENCY);
        if (electricalCapacity < 1) {
            generateElectricity();
        }
        return 9.81 * time * time / 8;
    }

    public void movement(Robot robot) throws NoEnergyException {
        double range = distance(robot);
        double remainingDistance = range;
        try {
            while (range > 0.01) {
                if (electricalCapacity < 1) {
                    generateElectricity();
                }
                electricalCapacity -= 1 + 1 * (1 - EFFICIENCY);
                range -= 0.1;
            }
            setX(robot.x);
            setY(robot.y);
        } catch (NoEnergyException e) {
            double distanceRatio = (range - remainingDistance) / range;
            setX(robot.x * distanceRatio);
            setY(robot.y * distanceRatio);
        }
    }

    public void generateElectricity() throws NoEnergyException {
        while (electricalCapacity < 100 & mineralFuel > 0.01) {
            electricalCapacity++;
            mineralFuel -= 0.1;
        }
        if (mineralFuel < 0.01 && electricalCapacity < 1) {
            throw new NoEnergyException("ElectricalCapacity now is below zero!");
        }
    }
}
