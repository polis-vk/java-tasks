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
        if (a == 0 && b == 0) { // Частный случай: Линейное уравнение
            x1 = (double) -d / c;
            return x1 + ", " + x1 + ", " + x1;
        }
        double x2;
        if (a == 0) { // Частный случай: Квадратное уравнение
            double rootOfDiscriminant = Math.sqrt(c * c - 4 * b * d);
            x1 = (-c + rootOfDiscriminant) / (2 * b);
            x2 = (-c - rootOfDiscriminant) / (2 * b);
            return x1 + ", " + x2 + ", " + x2;
        }
        // Решаем кубическое уравнение с помощью формулы Кардано
        double p = (double) (3 * a * c - b * b) / (3 * Math.pow(a, 2));
        double q = (2 * Math.pow(b, 3) - 9 * a * b * c + 27 * Math.pow(a, 2) * d) / (27 * Math.pow(a, 3));
        double bigQ = Math.pow(p, 3) / 27 + Math.pow(q, 2) / 4;
        if (isEquals(p, 0.0) && isEquals(q, 0.0) && isEquals(bigQ, 0.0)) { // Частный случай: 3 вещественных корня равны
            double quotient = (double) d / a;
            if (quotient > 0) {
                x1 = Math.pow(quotient, -1.0 / 3.0);
            } else {
                x1 = Math.pow(-quotient, 1.0 / 3.0);
            }
            return x1 + ", " + x1 + ", " + x1;
        }
        // 3 разных вещественных корня
        double tmp1 = Math.sqrt(Math.pow(q, 2) / 4 - bigQ);
        double tmp2 = Math.pow(tmp1, 1.0 / 3.0);
        double tmp3= Math.acos(-q / (2 * tmp1));
        double firstTerm = Math.cos(tmp3 / 3);
        double secondTerm = Math.sqrt(3) * Math.sin(tmp3 / 3);
        double lastTerm = (double) b / (3 * a);
        x1 = 2 * tmp2 * firstTerm - lastTerm;
        x2 = -tmp2 * (firstTerm + secondTerm) - lastTerm;
        if (x2 > x1) {
            double tmp = x2;
            x2 = x1;
            x1 = tmp;
        }
        double x3 = -tmp2 * (firstTerm - secondTerm) - lastTerm;
        if (x3 > x2) {
            double tmp = x3;
            x3 = x2;
            x2 = tmp;
            if (x2 > x1) {
                x2 = x1;
                x1 = tmp;
            }
        }
        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (!isEquals(a1, a2)) {
            return 0;
        }
        return (float) (Math.abs(b2 - b1) / Math.sqrt(a1 * a1 + 1));
    }

    public static boolean isEquals(double o1, double o2) {
        final double EPSILON = 1e-10;
        return Math.abs(o1 - o2) <= EPSILON;
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
        // Матрица, детерминант которой, является уравнением плоскости
        int[][] a = {
                    {x4 - x1, y4 - y1, 0},
                    {x2 - x1, y2 - y1, z2 - z1},
                    {x3 - x1, y3 - y1, z3 - z1}
        };
        // Формула полученная из детерминанта матрицы
        return (double) (a[0][1] * (a[1][0] * a[2][2] - a[2][0] * a[1][2])
                - a[0][0] * (a[1][1] * a[2][2] - a[2][1] * a[1][2]))
                / (double) (a[1][0] * a[2][1] - a[2][0] * a[1][1]) + (double) z1;

    }

}
