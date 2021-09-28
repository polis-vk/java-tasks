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
        // Решение по формуле Кардано

        // приведение к канонической форме
        double[] roots = solveCubic(
                (double) c / a - b * b / (3.0 * a * a),
                2.0 * b * b * b / (27.0 * a * a * a) - ((double) b) * c / (3.0 * a * a) + (double) d / a
        );
        // обратная замена переменных
        for (int i = 0; i < 3; i++) {
            roots[i] -= b / (3.0 * a);
        }

        return roots[2] + ", " + roots[1] + ", " + roots[0];
    }

    /**
     * Корни кубического уравнения в канонической форме y^3 + py + q = 0.
     * <p>
     * Находятся по формуле Кардано
     *
     * @param p коэффициент при <code>y</code>
     * @param q свободный коэффициент
     * @return Массив корней. <code>result[0] <= result[1] <= result[2]</code>
     */
    private static double[] solveCubic(double p, double q) {
        final double EPSILON = 1E-5;
        // 1 однократный корень и 1 двукратный
        if (Math.abs(Math.pow(-p / 3.0, 3.0) + Math.pow(0.5 * q, 2.0)) < EPSILON) {
            if (Math.abs(p - q) < EPSILON) {
                return new double[]{0.0, 0.0, 0.0};
            }

            double alpha = Math.cbrt(-0.5 * q);
            double[] result = new double[]{2.0 * alpha, -alpha, -alpha};

            Arrays.sort(result);
            return result;
        }
        // 3 вещественных корня
        double t = Math.acos(0.5 * q / Math.pow(-p / 3.0, 1.5)) / 3.0;
        double[] result = new double[]{-1.0, 0.0, 1.0};
        for (int i = 0; i < 3; i++) {
            result[i] = -2.0 * Math.sqrt(-p / 3.0) * Math.cos(t + 2.0 * Math.PI * result[i] / 3.0);
        }

        Arrays.sort(result);
        return result;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * a1 x + b1 = 0 и a2 x + b2 = 0
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        final double EPSILON = 1E-5;
        if (Math.abs(a1 - a2) >= EPSILON) {
            // lines intersect each other
            return 0.0f;
        }
        // lines are parallel
        // https://en.wikipedia.org/wiki/Distance_between_two_parallel_lines#Formula_and_proof
        return (float) (Math.abs(b2 - b1) / Math.sqrt(a1 * a1 + 1));
    }

    /**
     * Даны три точки в пространстве (x1, y1, z1), (x2, y2, z2) и (x3, y3, z3). Вам нужно найти Z координату
     * четвертой точки (x4, y4, z4), которая находится на той же плоскости, что и первые три.
     * (0, 0, 1,
     * 1, 1, 1,
     * 10, 100, 1,
     * 235, -5) -> 1
     */
    public static double surfaceFunction(int x1, int y1, int z1,
                                         int x2, int y2, int z2,
                                         int x3, int y3, int z3,
                                         int x4, int y4) {
        // solution of equation over z4: det(M) = 0, where
        //     | x4 - x1  x3 - x1  x2 - x1 |
        // M = | y4 - y1  y3 - y1  y2 - y1 |
        //     | z4 - z1  z3 - z1  z2 - z1 |
        return z1 - (double) (
                (x4 - x1) * ((y3 - y1) * (z2 - z1) - (z3 - z1) * (y2 - y1))
                        - (y4 - y1) * ((x3 - x1) * (z2 - z1) - (z3 - z1) * (x2 - x1))
        ) / ((x3 - x1) * (y2 - y1) - (y3 - y1) * (x2 - x1));
    }
}
