package ru.mail.polis.homework.simple;

import java.util.Arrays;
import java.util.Collections;

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
        //I used Vieta's trigonometric formula
        double aPriv = (double) b / a;
        double bPriv = (double) c / a;
        double cPriv = (double) d / a;
        double Q = (Math.pow(aPriv, 2) - 3 * bPriv) / 9;
        double R = (2 * Math.pow(aPriv, 3) - 9 * aPriv * bPriv + 27 * cPriv) / 54;
        double S = Math.pow(Q, 3) - Math.pow(R, 2);

        Double[] roots = new Double[3];

        if (S == 0) {
            roots[0] = -2 * Math.pow(R, 1 / 3d) - aPriv / 3;
            roots[1] = Math.pow(R, 1 / 3d) - aPriv / 3;
            roots[2] = roots[1];
        } else {
            double fi = Math.acos(R / Math.pow(Q, 3d / 2)) / 3;
            roots[0] = -2 * Math.pow(Q, 1d / 2) * Math.cos(fi) - aPriv / 3d;
            roots[1] = -2 * Math.pow(Q, 1d / 2) * Math.cos(fi + 2 * Math.PI / 3) - aPriv / 3d;
            roots[2] = -2 * Math.pow(Q, 1d / 2) * Math.cos(fi - 2 * Math.PI / 3) - aPriv / 3d;
        }

        Arrays.sort(roots, Collections.reverseOrder());
        return roots[0] + ", " + roots[1] + ", " + roots[2];
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (a1 != a2)
            return 0;
        //For canonic d = |C1-C2|/VA1^2+B1^2, for this form d = |b1-b2|/Va^2+1
        return (float) (Math.abs(b1 - b2) / Math.sqrt(Math.pow(a1, 2) + 1));
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
        double A = (y2 - y1) * (z3 - z1) - (y3 - y1) * (z2 - z1);
        double B = (z2 - z1) * (x3 - x1) - (z3 - z1) * (x2 - x1);
        double C = (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1);
        double D = -x1 * (y2 - y1) * (z3 - z1) - z1 * (x2 - x1) * (y3 - y1) - y1 * (z2 - z1) * (x3 - x1)
                + z1 * (x3 - x1) * (y2 - y1) + x1 * (y3 - y1) * (z2 - z1) + y1 * (x2 - x1) * (z3 - z1);

        double z4 = -(A * x4 + B * y4 + D) / C;
        return z4;
    }
}
