package ru.mail.polis.homework.simple;

import java.util.Arrays;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {

    final static double EPS = 0.000000001d;

    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */


    public static String equation(int a, int b, int c, int d) {
        // Vieta's theorem for cubic equations
        double[] x = new double[3];
        double A = (double) b / a;
        double B = (double) c / a;
        double C = (double) d / a;
        double q = (Math.pow(A, 2) - 3 * B) / 9;
        double r = (2 * Math.pow(A, 3) - 9 * A * B + 27 * C) / 54;
        double s = Math.pow(q, 3) - Math.pow(r, 3);
        if (s > 0) {
            double fi = Math.acos(r / Math.sqrt(Math.pow(q, 3))) / 3;
            x[0] = -2 * Math.sqrt(q) * Math.cos(fi) - A / 3;
            x[1] = -2 * Math.sqrt(q) * Math.cos(fi + 2 * Math.PI / 3) - A / 3;
            x[2] = -2 * Math.sqrt(q) * Math.cos(fi - 2 * Math.PI / 3) - A / 3;
        }
        if (s == 0) {
            x[0] = -2 * Math.cbrt(r) - A / 3;
            x[2] = x[1] = Math.cbrt(r) - A / 3;
        }

        Arrays.sort(x);

        return x[2] + ", " + x[1] + ", " + x[0];

    }


    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (Math.abs(a1 - a2) < DoubleAdvancedTask.EPS) {
            return (float) (Math.abs(b2 - b1) / Math.sqrt(a1 * a1 + 1));
        }
        return 0;
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
        double a = y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);
        double b = z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);
        double c = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
        return (a * x1 + b * y1 + c * z1 - a * x4 - b * y4) / c;
    }
}
