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
        final double ERROR = 0.000001;
        double newA = (double) b / a;
        double newB = (double) c / a;
        double newC = (double) d / a;
        double q = (Math.pow(newA, 2) - 3 * newB) / 9;
        double r = (2 * Math.pow(newA, 3) - 9 * newA * newB + 27 * newC) / 54;
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;
        if (Math.abs(Math.pow(q, 3) - Math.pow(r, 2)) < ERROR) {
            x1 = -2 * Math.cbrt(r) - newA / 3;
            x2 = Math.cbrt(r) - newA / 3;
            x3 = 0;
        } else {
            double fi = (double) 1 / 3 * Math.acos(r / Math.pow(q, (double) 3 / 2));
            x1 = -2 * Math.sqrt(q) * Math.cos(fi) - newA / 3;
            x2 = -2 * Math.sqrt(q) * Math.cos(fi - Math.PI * 2 / 3) - newA / 3;
            x3 = -2 * Math.sqrt(q) * Math.cos(fi + Math.PI * 2 / 3) - newA / 3;
        }
        double[] results = Arrays.stream(new double[]{x1, x2, x3}).sorted().toArray();
        return results[2] + ", " + results[1] + ", " + results[0];
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        final double ERROR = 0.000001;
        if (Math.abs(a1 - a2) > ERROR) {
            return 0;
        }
        return (float) (Math.cos(Math.atan(a1)) * Math.abs(b2 - b1));
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
        double a = (y2 - y1) * (z3 - z1) - (y3 - y1) * (z2 - z1);
        double b = ((x2 - x1) * (z3 - z1) - (x3 - x1) * (z2 - z1));
        double c = (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1);
        return ((y4 - y1) * b - (x4 - x1) * a) / c + z1;
    }
}
