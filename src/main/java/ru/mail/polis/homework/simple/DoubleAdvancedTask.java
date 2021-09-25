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
        double b2 = (double) b / a;
        double c2 = (double) c / a;
        double d2 = (double) d / a;
        //Тригонометрическая формула Виета
        double Q = (Math.pow(b2, 2) - 3 * c2) / 9;
        double R = (2 * Math.pow(b2, 3) - 9 * b2 * c2 + 27 * d2) / 54;
        double S = Math.pow(Q, 3) - Math.pow(R, 2);
        double[] roots = new double[3];
        if (S > 0) {
            double phi = Math.acos(R / Math.sqrt(Math.pow(Q, 3))) / 3;
            roots[0] = -2 * Math.sqrt(Q) * Math.cos(phi) - b2 / 3.0;
            roots[1] = -2 * Math.sqrt(Q) * Math.cos(phi + (2 * Math.PI) / 3) - b2 / 3.0;
            roots[2] = -2 * Math.sqrt(Q) * Math.cos(phi - (2 * Math.PI) / 3) - b2 / 3.0;
        } else {
            roots[0] = -2 * Math.cbrt(R) - b2 / 3.0;
            roots[1] = Math.cbrt(R) - b2 / 3.0;
            roots[2] = roots[1];
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
        if (a1 != a2) {
            return 0;
        } else {
            return (float) (Math.abs(b1 - b2) / Math.sqrt(a1 * a1 + 1));
        }
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
        float a1 = x2 - x1;
        float b1 = y2 - y1;
        float c1 = z2 - z1;
        float a2 = x3 - x1;
        float b2 = y3 - y1;
        float c2 = z3 - z1;

        float a = b1 * c2 - b2 * c1;
        float b = a2 * c1 - a1 * c2;
        float c = a1 * b2 - b1 * a2;
        float d = (-a * x1 - b * y1 - c * z1);
        return (-a * x4 - b * y4 - d) / c;
    }
}
