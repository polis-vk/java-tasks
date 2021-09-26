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
        // Тригонометрическая формула Виета
        // x^3 + mx^2 + nx + k = 0
        final double m = (double) b / a;
        final double n = (double) c / a;
        final double k = (double) d / a;
        final double q = (Math.pow(m, 2) - 3 * n) / 9;
        final double r = (2 * Math.pow(m, 3) - 9 * m * n + 27 * k) / 54;
        final double s = Math.pow(q, 3) - Math.pow(r, 2);
        double[] roots = new double[3];
        if (s > 0) {
            final double phi = Math.acos(r / Math.pow(q, 3d / 2)) / 3;
            roots[0] = -2 * Math.sqrt(q) * Math.cos(phi) - m / 3;
            roots[1] = -2 * Math.sqrt(q) * Math.cos(phi + Math.PI * 2d / 3) - m / 3;
            roots[2] = -2 * Math.sqrt(q) * Math.cos(phi - Math.PI * 2d / 3) - m / 3;
        } else {
            roots[0] = -2 * Math.cbrt(r) - m / 3;
            roots[2] = roots[1] = Math.cbrt(r) - m / 3;
        }
        Arrays.sort(roots);
        final double x3 = roots[0];
        final double x2 = roots[1];
        final double x1 = roots[2];
        return x1 + ", " + x2 + ", " + x3;
    }


    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */

    public static float length(double a1, double b1, double a2, double b2) {
        // При a1 = a2 прямые не параллельны и пересекаются
        return a1 == a2 ? (float) (Math.abs(b2 - b1) / Math.sqrt(a1 * a1 + 1)) : 0;
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

        final double a = y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);
        final double b = z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);
        final double c = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
        final double d = -(x1 * (y2 * z3 - y3 * z2) + x2 * (y3 * z1 - y1 * z3) + x3 * (y1 * z2 - y2 * z1));
        // Получили коэффициенты уравнения плоскости, откуда выразим z:
        return (-d - b * y4 - a * x4) / c;
    }
}
