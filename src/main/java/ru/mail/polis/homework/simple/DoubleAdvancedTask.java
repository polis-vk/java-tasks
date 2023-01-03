package ru.mail.polis.homework.simple;

import java.util.Arrays;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */


public class DoubleAdvancedTask {
//    public static void main(String[]args){
//        System.out.println(equation(1, -4, -7, 10));
//    }

    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */

//    Подстановка Виета
    public static String equation(int a, int b, int c, int d) {
        double[] results = {0, 0, 0};

        double a_new = (double) b / a;
        double b_new = (double) c / a;
        double c_new = (double) d / a;

        final double q = (Math.pow(a_new, 2) - 3 * b_new) / 9;
        final double r = ((2 * Math.pow(a_new, 3)) - (9 * a_new * b_new) + (27 * c_new)) / 54;
        final double s = Math.pow(q, 3) - Math.pow(r, 2);
        if (s > 0) {
            double phi = Math.acos(r / (Math.sqrt(q * q * q))) / 3;
            results[0] = -2 * Math.sqrt(q) * Math.cos(phi) - a_new / 3;
            results[1] = -2 * Math.sqrt(q) * Math.cos(phi + 2 * Math.PI / 3) - a_new / 3;
            results[2] = -2 * Math.sqrt(q) * Math.cos(phi - 2 * Math.PI / 3) - a_new / 3;
        } else if (s < 0) {
            int sign = Integer.signum((int) r);
            double temp = Math.sqrt(Math.pow(r, 2) - Math.pow(q, 3));
            double a_tm = -1 * sign * Math.cbrt(Math.abs(r) + temp);
            double b_tm = q / a_tm;
            results[0] = (a_tm + b_tm) - a_new / 3;
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
        if (a1 != a2) {
            return 0;
        }
        double x1 = 0;
        double y1 = b1;
        float ans = (float) ((x1 * a2 - y1 * 1 + b2) / Math.sqrt(a2 * a2 + 1));
        if (ans > 0) {
            return ans;
        }
        return -ans;
    }

    /**
     * Даны три точки в пространстве (x1, y1, z1) , (x2, y2, z2) и (x3, y3, z3). Вам нужно найти Z координату
     * четвертой точки (x4, y4, z4), которая находится на той же плоскости что и первые три.
     * (0, 0, 1,
     * 1, 1, 1,
     * 10, 100, 1,
     * 235, -5) -> 1
     * <p>
     * Ax1 + By1 + Cz1 + D = 0
     * Ax2 + By2 + Cz2 + D = 0
     * Ax3 + By3 + Cz3 + D = 0
     */
    public static double surfaceFunction(int x1, int y1, int z1,
                                         int x2, int y2, int z2,
                                         int x3, int y3, int z3,
                                         int x4, int y4) {
        int matrix[][] = {
                {x2 - x1, y2 - y1, z2 - z1},
                {x3 - x1, y3 - y1, z3 - z1}
        };

        int a = matrix[0][1] * matrix[1][2] - matrix[0][2] * matrix[1][1];
        int b = -(matrix[0][0] * matrix[1][2] - matrix[0][2] * matrix[1][0]);
        int c = matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        int d = -(a * x1 + b * y1 + c * z1);

        return (double) -(a * x4 + b * y4 + d) / c;

    }
}
