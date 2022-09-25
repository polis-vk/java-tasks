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

    //алгоритм для решения кубического уравнения методом Виета — Кардано
    public static String equation(int a, int b, int c, int d) {
        double[] roots = new double[3];

        double a1 = (double) b / a;
        double b1 = (double) c / a;
        double c1 = (double) d / a;

        double q = (a1 * a1 - 3 * b1) / 9;
        double r = (2 * Math.pow(a1, 3) - 9 * a1 * b1 + 27 * c1) / 54;
        if (q == 0) {
            double A = -Math.signum(r) * (Math.pow(Math.abs(r) + Math.sqrt(r * r - Math.pow(q, 3)), (double) 1 / 3));
            double B = (A == 0) ? 0 : q / A;
            roots[0] = (A + B) - a1 / 3;
            roots[1] = roots[2] = -A - a1 / 3;

        } else {
            double t = Math.acos(r / Math.sqrt(Math.pow(q, 3))) / 3;
            roots[0] = -2 * Math.sqrt(q) * Math.cos(t) - (a1 / 3);
            roots[1] = -2 * Math.sqrt(q) * Math.cos(t + (2 * Math.PI / 3)) - (a1 / 3);
            roots[2] = -2 * Math.sqrt(q) * Math.cos(t - (2 * Math.PI / 3)) - (a1 / 3);
        }

        Arrays.sort(roots);
        double x1 = roots[2];
        double x2 = roots[1];
        double x3 = roots[0];

        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (a1 == a2) {
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
        /*
            |11 12 13|          |x4-x1  y4-y1   z4-z1|
            |21 22 23| = 0 =>   |x2-x1  y2-y1   z2-z1| = 0 => place13 = z4 - z1 -?
            |31 32 33|          |x3-x1  y3-y1   z3-z1|

         */
        double place11 = x4 - x1;
        double place12 = y4 - y1;

        double place21 = x2 - x1;
        double place22 = y2 - y1;
        double place23 = z2 - z1;

        double place31 = x3 - x1;
        double place32 = y3 - y1;
        double place33 = z3 - z1;


        return z1 + ((place11 * place23 * place32 + place12 * place21 * place33 -
                place11 * place22 * place33 - place12 *
                place23 * place31) / (place21 * place32 - place22 * place31));
    }
}
