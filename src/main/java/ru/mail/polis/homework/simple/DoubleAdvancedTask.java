package ru.mail.polis.homework.simple;

import java.util.Arrays;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {

    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */
    public static String equation(int a, int b, int c, int d) {
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;

        double q = (Math.pow(b, 2) - 3.0 * a * c) / (9.0 * Math.pow(a, 2));
        double r = (2.0 * Math.pow(b, 3) - 9.0 * a * b * c + 27.0 * Math.pow(a, 2) * d) / (54.0 * Math.pow(a, 3));

        double s = Math.pow(q, 3) - Math.pow(r, 2);

        double[] x = new double[3];

        if (s > 0) {
            double ph = Math.acos(r / Math.sqrt(Math.pow(q, 3))) / 3.0;

            x[0] = -2 * Math.sqrt(q) * Math.cos(ph) - b / (3.0 * a);
            x[1] = -2 * Math.sqrt(q) * Math.cos(ph + 2.0 * Math.PI / 3.0) - b / (3.0 * a);
            x[2] = -2 * Math.sqrt(q) * Math.cos(ph - 2.0 * Math.PI / 3.0) - b / (3.0 * a);
        } else {
            x[0] = -2 * Math.signum(r) * Math.sqrt(q) - b / (3.0 * a);
            x[1] = x[2] = Math.signum(r) * Math.sqrt(q) - b / (3.0 * a);
        }

        Arrays.sort(x);

        x1 = x[2];
        x2 = x[1];
        x3 = x[0];

        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return (Math.abs(a2 - a1) < 1e-6 ? (float)(Math.abs(b2 - b1) / Math.sqrt(Math.pow(a2, 2) + 1)) : 0);
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
        double A = (y2 - y1) * (z3 - z1) - (z2 - z1) * (y3 - y1);
        double B = -((x2 - x1) * (z3 - z1) - (z2 - z1) * (x3 - x1));
        double C = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);
        double D = -x1 * A - y1 * B - z1 * C;

        return -(A * x4 + B * y4 + D) / C;
    }
}
