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
    private static final double EPS = 0.000001;

    public static String equation(int a, int b, int c, int d) {

        double ratioA = (double) b / a;
        double ratioB = (double) c / a;
        double ratioC = (double) d / a;

        double p = ratioB - ratioA * ratioA / 3;
        double q = 2 * ratioA * ratioA * ratioA / 27 - ratioA * ratioB / 3 + ratioC;
        double discriminant = p * p * p + q * q;

        double[] roots = new double[]{0, 0, 0};

        if (Math.abs(discriminant) < EPS) {
            roots[0] = -2 * Math.pow(p / 2, 1.0 / 3) - ratioA / 3;
            roots[1] = -Math.pow(p / 2, 1.0 / 3) - ratioA / 3;
            roots[2] = roots[1];
        } else if (discriminant > 0) {
            roots[0] = Math.pow(-q / 2 + Math.sqrt(discriminant), 1.0 / 3) +
                    Math.pow(-q / 2 - Math.sqrt(discriminant), 1.0 / 3) - ratioA / 3;
            return roots[0] + ", " + roots[0] + ", " + roots[0];
        } else {
            double ratio = 2 / Math.sqrt(3) * Math.sqrt(-p);
            double argArcSin = (3 * Math.sqrt(3) * q) / (2 * Math.pow(Math.sqrt(-p), 3));

            if (Math.abs(argArcSin + 1) < EPS) {
                argArcSin = -1;
            } else if (Math.abs(argArcSin - 1) < EPS) {
                argArcSin = 1;
            }

            double arcSin = Math.asin(argArcSin) / 3;

            roots[0] = ratio * Math.sin(arcSin) - ratioA / 3;
            roots[1] = -ratio * Math.sin(arcSin + Math.PI / 3) - ratioA / 3;
            roots[2] = ratio * Math.cos(arcSin + Math.PI / 6) - ratioA / 3;
        }

        Arrays.sort(roots);

        return roots[2] + ", " + roots[1] + ", " + roots[0];
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return a1 == a2 ? (float) (Math.abs(b2 - b1) / Math.sqrt(Math.pow(a1, 2) + 1)) : 0;
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
        double a = (y2 - y1) * (z3 - z1) - (z2 - z1) * (y3 - y1);
        double b = -((x2 - x1) * (z3 - z1) - (z2 - z1) * (x3 - x1));
        double c = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);
        double d = -x1 * a - y1 * b - z1 * c;

        return (-d - a * x4 - b * y4) / c;
    }
}
