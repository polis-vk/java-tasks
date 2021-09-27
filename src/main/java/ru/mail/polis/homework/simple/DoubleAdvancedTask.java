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
        // Используется тригонометрическая формула Виета (Википедия).
        double A = (double) b / a;
        double B = (double) c / a;
        double C = (double) d / a;
        double Q = (A * A - 3 * B) / 9;
        double R = (2 * A * A * A - 9 * A * B + 27 * C) / 54;
        double S = Q * Q * Q - R * R;
        double[] ans = new double[3];
        if (S == 0.0) {
            ans[0] = -2 * Math.signum(R) * Math.sqrt(Q) - (A / 3);
            ans[1] = Math.signum(R) * Math.sqrt(Q) - (A / 3);
            ans[2] = ans[1];
        } else {
            double f = (1.0 / 3.0) * Math.acos(R / Math.sqrt(Q * Q * Q));
            ans[0] = -2 * Math.sqrt(Q) * Math.cos(f) - (A / 3);
            ans[1] = -2 * Math.sqrt(Q) * Math.cos(f + ((2 * Math.PI) / 3.0)) - (A / 3);
            ans[2] = -2 * Math.sqrt(Q) * Math.cos(f - ((2 * Math.PI) / 3.0)) - (A / 3);
        }
        Arrays.sort(ans);
        return ans[2] + ", " + ans[1] + ", " + ans[0];
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
        // Используется формула нахождения расстояния между двумя прямыми,
        // представленными в виде общего уравнения прямой.
        return (float) (Math.abs(b2 - b1) / Math.sqrt(a1 * a2 + 1));
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
        // Координаты векторов между прямыми на плоскости.
        int dx21 = x2 - x1;
        int dy21 = y2 - y1;
        int dz21 = z2 - z1;

        int dx31 = x3 - x1;
        int dy31 = y3 - y1;
        int dz31 = z3 - z1;

        int dx41 = x4 - x1;
        int dy41 = y4 - y1;

        // Нормаль к плоскости.
        int ni = dy21 * dz31 - dy31 * dz21;
        int nj = -(dx21 * dz31 - dx31 * dz21);
        int nk = dx21 * dy31 - dx31 * dy21;

        // z4 получается из скалярного произведения нормали и вектора на плоскости,
        // проходящего через четвертую точку (оно будет равно нулю).
        return (nk * z1 - ni * dx41 - nj * dy41) / ((double) nk);
    }
}
