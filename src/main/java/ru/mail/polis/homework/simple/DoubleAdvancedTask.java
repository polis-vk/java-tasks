package ru.mail.polis.homework.simple;

import java.util.ArrayList;
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
        double a1 = b / (double) a;
        double b1 = c / (double) a;
        double c1 = d / (double) a;
        double Q = (a1 * a1 - 3 * b1) / 9;
        double R = (2 * a1 * a1 * a1 - 9 * a1 * b1 + 27 * c1) / 54;
        if (Q * Q * Q > R * R) {
            double fi = Math.acos(R / Math.sqrt(Q * Q * Q)) / 3;
            x1 = -2 * Math.sqrt(Q) * Math.cos(fi) - a1 / 3;
            x2 = -2 * Math.sqrt(Q) * Math.cos(fi + 2 * Math.PI / 3) - a1 / 3;
            x3 = -2 * Math.sqrt(Q) * Math.cos(fi - 2 * Math.PI / 3) - a1 / 3;
        } else {
            x1 = -2 * Math.cbrt(R) - a1 / 3;
            x2 = x3 = Math.cbrt(R) - a1 / 3;
        }
        double[] arr = new double[]{x1, x2, x3};
        Arrays.sort(arr);
        return arr[2] + ", " + arr[1] + ", " + arr[0];
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (Math.abs(a1 - a2) > 1e-10) {
            return 0;
        }
        return (float) (Math.abs(b2 - b1) / Math.sqrt(a1 * a2 + 1));
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
        return ((x2 - x1) * (y4 - y1) * (z3 - z1) - (x3 - x1) * (y4 - y1) * (z2 - z1) - (x4 - x1) * (y2 - y1) * (z3 - z1) + (x4 - x1) * (y3 - y1) * (z2 - z1)) / (double) ((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1)) + z1;
    }
}
