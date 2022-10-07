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
     * Константа для более точного сравнения double и float.
     */
    private static final double EPSILON = 0.000001;

    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */
    public static String equation(int a, int b, int c, int d) {
        /*
         * Метод Виета-Кардано.
         * Уравнение имеет вид: x^3 + a * x^2 + b * x + c = 0
         * При S > 0 уравнение имеет 3 действительных корня.
         * При S < 0 уравнение имеет 1 действительный и пару комплексных корней.
         * При S = 0 уравнение вырождено и имеет меньше 3 различных решений.
         */
        double[] results = {0, 0, 0};

        double aV = (double) b / a;
        double bV = (double) c / a;
        double cV = (double) d / a;

        double Q = (Math.pow(aV, 2) - 3 * bV) / 9;
        double R = ((2 * Math.pow(aV, 3)) - (9 * aV * bV) + (27 * cV)) / 54;
        double S = Math.pow(Q, 3) - Math.pow(R, 2);

        if (S > 0) {
            double PHI = Math.acos(R / Math.sqrt(Math.pow(Q, 3))) / 3;
            results[0] = (-2 * Math.sqrt(Q) * Math.cos(PHI)) - aV / 3;
            results[1] = (-2 * Math.sqrt(Q) * Math.cos(PHI - 2 * Math.PI / 3)) - aV / 3;
            results[2] = (-2 * Math.sqrt(Q) * Math.cos(PHI + 2 * Math.PI / 3)) - aV / 3;
        } else if (S < 0) {
            int sign = Integer.signum((int) R);
            double temp = Math.sqrt(Math.pow(R, 2) - Math.pow(Q, 3));
            double A = -1 * sign * Math.cbrt(Math.abs(R) + temp);
            double B = Q / A;
            results[0] = (A + B) - aV / 3;
        }

        Arrays.sort(results);
        return results[2] + ", " + results[1] + ", " + results[0];
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (Math.abs(a1 - a2) > EPSILON) {
            return 0;
        }
        double numerator = Math.abs(b2 - b1);
        double denominator = Math.sqrt(Math.pow(a1, 2) + 1);
        return (float) (numerator / denominator);
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
         * Находим точку z4 с помощью уравнения плоскости.
         * Уравнение имеет вид: a * x + b * y + c * z + d = 0
         */
        double[][] coefs = {{x2 - x1, y2 - y1, z2 - z1}, {x3 - x1, y3 - y1, z3 - z1}};
        double a = coefs[0][1] * coefs[1][2] - coefs[1][1] * coefs[0][2];
        double b = coefs[1][0] * coefs[0][2] - coefs[0][0] * coefs[1][2];
        double c = coefs[0][0] * coefs[1][1] - coefs[0][1] * coefs[1][0];
        double d = -(a * x1 + b * y1 + c * z1);
        return (-a * x4 - b * y4 - d) / c;
    }

}