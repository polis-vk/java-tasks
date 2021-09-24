package ru.mail.polis.homework.simple;

import java.util.ArrayList;
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
     *
     */

    private static double[] solveDerivative(int a, int b, int c) {
        final double d = b * b - 4 * a * c;
        if (d > 0) {
            double x1, x2;
            x1 = (-b - Math.sqrt(d)) / (2 * a);
            x2 = (-b + Math.sqrt(d)) / (2 * a);
            return new double[]{x1, x2};
        } else if (d == 0) {
            double x;
            x = -b / (2d * a);
            return new double[]{x, x};
        } else {
            throw new IllegalArgumentException("Нет 2 корней экстремума");
        }
    }

    private static boolean isMoreThenRoot(double root, int a, int b, int c, int d) {
        return a * root * root * root + b * root * root + c * root + d > 0;
    }

    private static boolean isRoot(double root, int a, int b, int c, int d) {
        return a * root * root * root + b * root * root + c * root + d == 0;
    }

    private static double getOneRootInRange(double left, double right, int a, int b, int c, int d) {
        final double e = 1e-9;
        if (isRoot(right, a, b, c, d)) {
            return right;
        }
        if (isRoot(left, a, b, c, d)) {
            return left;
        }
        final boolean isDecreasing = isMoreThenRoot(right - e, a, b, c, d);
        while (right >= left) {
            final double mid = (left + right) / 2;
            if (isMoreThenRoot(mid, a, b, c, d)) {
                if (isDecreasing) {
                    right = mid - e / 100;
                } else {
                    left = mid + e / 100;
                }
            } else {
                if (isDecreasing) {
                    left = mid + e / 100;
                } else {
                    right = mid - e / 100;
                }
            }
        }
        return left;
    }

    public static String equation(int a, int b, int c, int d) {
        if (a < 0) {
            a *= -1;
            b *= -1;
            c *= -1;
            d *= -1;
        }
        final double[] extremum = solveDerivative(3 * a, 2 * b, c);
        /*
            (ax^3+bx^2+cx+d)' = 3ax^2 + 2bx + c
            Именно в нулях функции y = 3ax^2 + 2bx + c функция меняет знак
            На этих промежутках мы и будем искать корни
        */
        ArrayList<Double> arrayList = new ArrayList<>();
        arrayList.add(getOneRootInRange(Integer.MIN_VALUE, extremum[0], a, b, c, d));
        arrayList.add(getOneRootInRange(extremum[0], extremum[1], a, b, c, d));
        arrayList.add(getOneRootInRange(extremum[1], Integer.MAX_VALUE, a, b, c, d));
        Collections.sort(arrayList);
        final double x1 = arrayList.get(2);
        final double x2 = arrayList.get(1);
        final double x3 = arrayList.get(0);
        return x1 + ", " + x2 + ", " + x3;
    }


    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */

    public static float length(double a1, double b1, double a2, double b2) {
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
