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
    //(a!=0 && b!=0) || (a!=0) ,мы будем считать, что это так, иначе-не будет 3 вещественных корней.
    //Будем использовать формулу Кардано
    public static String equation(int a, int b, int c, int d) {
        double f = findF(a, b, c);
        double g = findG(a, b, c, d);
        if (f == 0 && g == 0) {
            double root = find3EqualRoot(a, d);
            return root + ", " + root + ", " + root;
        }
        double h = findH(f, g);
        double[] result = find3DifferentRoot(g, h, b, a);
        Arrays.sort(result);
        return result[2] + ", " + result[1] + ", " + result[0];
    }


    private static double find3EqualRoot(double a, double d) {
        double x1;
        if (d / a > 0) {
            x1 = Math.pow(d / a, -(1 / 3.0));
        } else if (d == 0) {
            x1 = 0;
        } else {
            x1 = Math.pow(-d / a, (1 / 3.0));
        }
        return x1;
    }

    private static double[] find3DifferentRoot(double g, double h, double b, double a) {
        double i = Math.sqrt(g * g / 4 - h);
        double j = Math.pow(i, 1 / 3.0);
        double k = Math.acos(-g / (2 * i));
        double l = -j;
        double m = Math.cos(k / 3.0);
        double n = Math.sqrt(3) * Math.sin(k / 3.0);
        double p = -(b / (3.0 * a));
        double x1 = 2 * j * Math.cos(k / 3.0) - (b / (3.0 * a));
        double x2 = l * (m + n) + p;
        double x3 = l * (m - n) + p;
        return new double[]{x1, x2, x3};
    }

    private static double findF(double a, double b, double c) {
        return (3.0 * a * c - b * b) / (3 * a * a);
    }

    private static double findG(double a, double b, double c, double d) {
        return (2 * b * b * b - 9 * a * b * c + 27 * a * a * d) / (27 * a * a * a);
    }

    private static double findH(double f, double g) {
        return Math.pow(f / 3, 3) + Math.pow(g / 2, 2);
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
        double denominator = (a1 == 0 && a2 == 0) ? 1 : Math.sqrt(a1 * a1 + 1);
        return (float) (Math.abs(b2 - b1) / denominator);
    }

    /**
     * Даны три точки в пространстве (x1, y1, z1) , (x2, y2, z2) и (x3, y3, z3). Вам нужно найти Z координату
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

        int leftPart;
        int[] v12 = {x2 - x1, y2 - y1, z2 - z1};//transformed
        int[] v13 = {x3 - x1, y3 - y1, z3 - z1};
        int[] v14 = {x4 - x1, y4 - y1};
        leftPart = -v12[1] * v13[2] * v14[0]
                - v13[0] * v14[1] * v12[2]
                + v14[0] * v13[1] * v12[2]
                + v14[1] * v13[2] * v12[0];
        return (double) leftPart / (v12[0] * v13[1] - v12[1] * v13[0]) + z1;
    }
}
