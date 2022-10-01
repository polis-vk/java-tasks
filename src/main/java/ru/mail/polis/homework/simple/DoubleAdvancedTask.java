package ru.mail.polis.homework.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {
    final static double DELTA = 0.000001;

    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */
    public static String equation(int a, int b, int c, int d) {
        List<Double> roots = new ArrayList<>();
        final double coefficientA = (double) b / a;
        final double cAo3 = coefficientA / 3;
        final double coefficientB = (double) c / a;
        final double coefficientC = (double) d / a;
        final double q = (3 * coefficientB - coefficientA * coefficientA) / 9;
        final double r = (9 * coefficientA * coefficientB - 27 * coefficientC - 2 * Math.pow(coefficientA, 3)) / 54;
        final double discriminant = Math.pow(q, 3) + r * r;
        if (discriminant > 0) {
            roots.add((Math.cbrt(r + Math.sqrt(discriminant)) + Math.cbrt(r - Math.sqrt(discriminant))) - cAo3);
            roots.add(0.0);
            roots.add(0.0);
        } else if (discriminant == 0) {
            roots.add(2 * Math.cbrt(r) - cAo3);
            roots.add(Math.cbrt(r) - cAo3);
            roots.add(Math.cbrt(r) - cAo3);
        } else {
            final double factor = 2 * Math.sqrt(-q);
            final double theta = Math.acos(r / Math.sqrt(-Math.pow(q, 3))) / 3;
            roots.add(factor * Math.cos(theta) - cAo3);
            roots.add(factor * Math.cos(theta + Math.PI * 2 / 3) - cAo3);
            roots.add(factor * Math.cos(theta + Math.PI * 4 / 3) - cAo3);
        }
        Collections.sort(roots);
        Collections.reverse(roots);
        double x1 = roots.get(0);
        double x2 = roots.get(1);
        double x3 = roots.get(2);
        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return Math.abs(a2 - a1) < DELTA ? (float) (Math.abs(b2 - b1) / Math.sqrt(a1 * a1 + 1)) : 0;
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
        int xzSurface = (x2 - x1) * (z3 - z1) - (z2 - z1) * (x3 - x1);
        int yzSurface = (y2 - y1) * (z3 - z1) - (z2 - z1) * (y3 - y1);
        int xySurface = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);
        return (double) ((y4 - y1) * xzSurface - (x4 - x1) * yzSurface) / xySurface + z1;
    }
}
