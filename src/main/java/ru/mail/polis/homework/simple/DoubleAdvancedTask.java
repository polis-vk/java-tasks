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

    //Используем Тригонометрическую формулу Виета
    public static String equation(int a, int b, int c, int d) {
        double[] roots = new double[3];

        double first = b * 1. / a;
        double second = c * 1. / a;
        double third = d * 1. / a;



        double q = (Math.pow(first, 2) - 3 * second) / 9;
        double r = (2 * Math.pow(first, 3) - 9 * first * second + 27 * third) / 54;
        double s = Math.pow(q, 3) - Math.pow(r, 2);

        if (s > 0) {
            double f = Math.acos(r / Math.sqrt(Math.pow(q, 3))) / 3 ;

            roots[0] = -2 * Math.sqrt(q) * Math.cos(f) - first / 3;
            roots[1] = -2 * Math.sqrt(q) * Math.cos(f + 2 * Math.PI / 3 ) - first / 3 ;
            roots[2] = -2 * Math.sqrt(q) * Math.cos(f - 2 * Math.PI / 3 ) - first / 3 ;


        }else {
            double t = Math.signum(r) * Math.sqrt(Math.abs(q));
            roots[0] = -2 * t - first / 3;
            roots[1] = t - first / 3;
            roots[2] = t - first / 3;
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
        if ( a2 - a1 == 0) {
            return  (float) ((Math.abs(b2 - b1)) / Math.sqrt(Math.pow(a1, 2) + 1));
        }
        return 0;
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


        int first = (y2 - y1) * (z3 - z1) - (z2 - z1) * (y3 - y1);
        int second = (x2 - x1) * (z3 - z1) - (z2 - z1) * (x3 - x1);
        int third = (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1);


        return ((double) (-(x4 - x1) * first + (y4 - y1) * second) / third) + z1 ;
    }
}
