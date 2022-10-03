package ru.mail.polis.homework.simple;

import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {

    private static final double EPS = 1e-10;

    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */
    public static String equation(int a, int b, int c, int d) {
        // Solution of the cubic equation by the Viet-Cardano method
        double A = (double) b / a;
        double B = (double) c / a;
        double C = (double) d / a;
        List<Double> result = new ArrayList<>();
        double q = (Math.pow(A, 2) - 3.0 * B) / 9.0;
        double r = (2.0 * Math.pow(A, 3) - 9 * A * B + 27.0 * C) / 54.0;
        double s = Math.pow(q, 3) - Math.pow(r, 2);
        double t;
        if (s > 0) {
            t = (Math.acos(r / Math.sqrt(Math.pow(q, 3)))) / 3.0;
            result.add(-2 * Math.sqrt(q) * Math.cos(t) - (A / 3.0));
            result.add(-2 * Math.sqrt(q) * Math.cos(t + (2.0 * Math.PI / 3.0)) - (A / 3.0));
            result.add(-2 * Math.sqrt(q) * Math.cos(t - (2.0 * Math.PI / 3.0)) - (A / 3.0));
        } else if (s == 0) {
            result.add(-2 * Math.pow(r, 1.0 / 3.0) - (A / 3.0));
            result.add(Math.pow(r, 1.0 / 3.0) - (A / 3.0));
            result.add(Math.pow(r, 1.0 / 3.0) - (A / 3.0));
        } else {
            result.add(-2 * Math.signum(r) * Math.sqrt(Math.abs(q)) - (A / 3.0));
            result.add(Math.signum(r) * Math.sqrt(Math.abs(q)) - (A / 3.0));
            result.add(Math.signum(r) * Math.sqrt(Math.abs(q)) - (A / 3.0));
        }
        result.sort(Comparator.naturalOrder());
        return result.get(2) + ", " + result.get(1) + ", " + result.get(0);
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (Math.abs(a1 - a2) > EPS) {
            return 0.0f;
        }
        return (float) (Math.abs(b2 - b1) / Math.sqrt((a2 * a1) + 1));
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
        double A = y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);
        double B = z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);
        double C = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
        double D = -(x1 * (y2 * z3 - y3 * z2) + x2 * (y3 * z1 - y1 * z3) + x3 * (y1 * z2 - y2 * z1));
        return -(A * x4 + B * y4 + D) / C;
    }
}
