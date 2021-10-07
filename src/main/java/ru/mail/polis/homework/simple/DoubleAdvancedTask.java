package ru.mail.polis.homework.simple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {
    public static final double ERROR = 0.00001;

    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */
    public static String equation(int k1, int k2, int k3, int k4) {
        double[] x = new double[3]; //Решение кубического уравнения при помощи тригонометрической формулы Виета
        double a = (double) k2 / k1;
        double b = (double) k3 / k1;
        double c = (double) k4 / k1;
        double q = (Math.pow(a, 2) - 3 * b) / 9;
        double r = (2 * Math.pow(a, 3) - 9 * a * b + 27 * c) / 54;
        if (Math.abs(Math.pow(q, 3) - Math.pow(r, 2)) > ERROR) {
            double t = 1.0 / 3.0 * Math.acos(r / Math.sqrt(Math.pow(q, 3)));
            x[0] = -2 * Math.sqrt(q) * Math.cos(t) - (double) a / 3;
            x[1] = -2 * Math.sqrt(q) * Math.cos(t + (2 * Math.PI / 3)) - ((double) a / 3);
            x[2] = -2 * Math.sqrt(q) * Math.cos(t - (2 * Math.PI / 3)) - ((double) a / 3);
        } else {
            x[0] = -2 * Math.cbrt(r) - (double) a / 3;
            x[1] = Math.cbrt(r) - (double) a / 3;
            x[2] = 0;
        }
        double temp;
        if (Math.max(Math.max(x[0], x[1]), x[2]) == x[1]) {
            temp = x[0];
            x[0] = x[1];
            x[1] = temp;
        }
        if (Math.max(Math.max(x[0], x[1]), x[2]) == x[2]) {
            temp = x[0];
            x[0] = x[2];
            x[2] = temp;
        }
        if (Math.min(x[1], x[2]) == x[1]) {
            temp = x[2];
            x[2] = x[1];
            x[1] = temp;
        }
        return x[0] + ", " + x[1] + ", " + x[2];
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (Math.abs(a1 - a2) > ERROR) {
            return 0;
        }
        return (float) Math.abs((b1 - b2) * Math.sin(Math.atan(1 / a1)));
    }

    /**
     * Даны три точки в пространстве (x1, y1, z1) , (x2, y2, z2) и (x3, y3, z3). Вам нужно найти Z координату
     * четвертой точки (x4, y4, z4), которая находится на той же плоскости что и первые три.
     * (0, 0, 1,
     * 1, 1, 1,
     * 10, 100, 1,
     * 235, -5) -> 1
     */
    public static double surfaceFunction(int x1, int y1, int z1,
                                         int x2, int y2, int z2,
                                         int x3, int y3, int z3,
                                         int x4, int y4) {
        if (((x1 == x2) && (y1 == y2) && (z1 == z2)) || ((x2 == x3) && (y2 == y3) && (z2 == z3))
                || ((x3 == x1) && (y3 == y1) && (z3 == z1))) {
            throw new IllegalArgumentException("Three point determine an infinite set of planes\n");
        }
        int a = y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);
        int b = z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);
        int c = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
        int d = -(x1 * (y2 * z3 - y3 * z2) + x2 * (y3 * z1 - y1 * z3) + x3 * (y1 * z2 - y2 * z1));
        return ((double) (-a * x4 - b * y4 - d) / c);
    }

}
