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
        double x1;
        double x2;
        double x3;
        double[] results = new double[3];

        double newB = (double) b / a;
        double newC = (double) c / a;
        double newD = (double) d / a;

        double Q = (Math.pow(newB, 2) - 3 * newC) / 9;
        double R = (2 * Math.pow(newB, 3) - 9 * newB * newC + 27 * newD) / 54;
        double S = Q * Q * Q - R * R;

        if (S > 0) {
            double fi = Math.acos(R / Math.sqrt(Q * Q * Q)) / 3;
            results[0] = -2 * Math.sqrt(Q) * Math.cos(fi) - newB / 3;
            results[1] = -2 * Math.sqrt(Q) * Math.cos(fi + 2 * Math.PI / 3) - newB / 3;
            results[2] = -2 * Math.sqrt(Q) * Math.cos(fi - 2 * Math.PI / 3) - newB / 3;
        } else {
            results[0] = -2 * Math.cbrt(R) - newB / 3;
            results[1] = Math.cbrt(R) - newB / 3;
            results[2] = results[1];
        }

        Arrays.sort(results);
        x1 = results[2];
        x2 = results[1];
        x3 = results[0];
        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (a1 != a2) {
            return 0;
        }
        return (float) (Math.abs(b2 - b1) / Math.sqrt(a1 * a1 + 1));
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
        int xCoefficient = (y2 - y1) * (z3 - z1) - (z2 - z1) * (y3 - y1);
        int yCoefficient = -1 * ((x2 - x1) * (z3 - z1) - (z2 - z1) * (x3 - x1));
        int zCoefficient = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);
        int d = -1 * yCoefficient * y1 - xCoefficient * x1 - zCoefficient * z1;
        return -1 * ((double) x4 * xCoefficient + y4 * yCoefficient + d) / zCoefficient;
    }
}
