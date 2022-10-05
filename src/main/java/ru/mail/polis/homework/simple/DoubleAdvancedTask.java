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
    private static final double EPS = 1e-10;

    public static String equation(int a, int b, int c, int d) {
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;
        // Тут используется тригонометрическая формула Виета решения кубических уравнений
        double a_new = b * Math.pow(a, -1);
        double b_new = c * Math.pow(a, -1);
        double c_new = d * Math.pow(a, -1);
        double Q = (Math.pow(a_new, 2) - 3 * b_new) * Math.pow(9, -1);
        double R = (2 * Math.pow(a_new, 3) - 9 * a_new * b_new + 27 * c_new) * Math.pow(54, -1);
        double S = Math.pow(Q, 3) - Math.pow(R, 2);
        if (S > 0) {
            double phi = (Math.acos(R * Math.pow(Q, -1.5))) * Math.pow(3, -1);
            x3 = -2 * Math.sqrt(Q) * Math.cos(phi) - a_new * Math.pow(3, -1);
            x1 = -2 * Math.sqrt(Q) * Math.cos(phi + 2 * Math.PI * Math.pow(3, -1)) - a_new * Math.pow(3, -1);
            x2 = -2 * Math.sqrt(Q) * Math.cos(phi - 2 * Math.PI * Math.pow(3, -1)) - a_new * Math.pow(3, -1);
        } else if (S == 0) {
            x1 = -2 * Math.cbrt(R) - a_new * Math.pow(3, -1);
            x2 = Math.cbrt(R) - a_new * Math.pow(3, -1);
            x3 = x2;
        } else {
            double x = (Math.abs(R) * Math.pow(Math.abs(Q), -3 * Math.pow(2, -1)));
            double phi = (Math.log(x + Math.sqrt(Math.pow(x, 2) - 1))) * Math.pow(3, -1);
            x1 = -2 * Math.signum(R) * Math.sqrt(Math.abs(Q)) * Math.cosh(phi) - a_new * Math.pow(3, -1);
        }
        double[] array = new double[]{x1, x2, x3};
        Arrays.sort(array);
        x1 = array[2];
        x2 = array[1];
        x3 = array[0];
        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        float distance = 0;
        if (Math.abs(a1 - a2) < EPS) {
            distance = (float) (Math.abs(b2 - b1) * Math.pow(Math.sqrt(Math.pow(a1, 2) + 1), -1));
        }
        return distance;
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
        int A = (y2 - y1) * (z3 - z1) - (y3 - y1) * (z2 - z1);
        int B = (z2 - z1) * (x3 - x1) - (x2 - x1) * (z3 - z1);
        int C = (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1);
        int D = -x1 * A - y1 * B - z1 * C;
        double z4 = (-A * x4 - B * y4 - D) * Math.pow(C, -1);
        return z4;
    }
}
