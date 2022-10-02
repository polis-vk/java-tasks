package ru.mail.polis.homework.simple;

import java.util.Arrays;
import java.util.Collections;

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
    // Уравнение решается при помощи тригонометрической формулы Виета.
    public static String equation(int a, int b, int c, int d) {
        double x1;
        double x2;
        double x3;

        double normalizedA = (double) b / a;
        double normalizedB = (double) c / a;
        double normalizedC = (double) d / a;

        double Q = (Math.pow(normalizedA, 2) - 3 * normalizedB) / 9;
        double R = (2 * Math.pow(normalizedA, 3) - 9 * normalizedA * normalizedB + 27 * normalizedC) / 54;
        double S = Math.pow(Q, 3) - Math.pow(R, 2);

        if (S > 0.0) {
            double fi = Math.acos(R / Math.pow(Q, 1.5)) / 3.0;
            x1 = -2 * Math.sqrt(Q) * Math.cos(fi) - normalizedA / 3.0;
            x2 = -2 * Math.sqrt(Q) * Math.cos(fi + 2 * Math.PI / 3) - normalizedA / 3.0;
            x3 = -2 * Math.sqrt(Q) * Math.cos(fi - 2 * Math.PI / 3) - normalizedA / 3.0;
        } else {
            x1 = -2 * Math.cbrt(R) - normalizedA / 3.0;
            x2 = Math.cbrt(R) - normalizedA / 3.0;
            x3 = x2;
        }
        Double[] roots = new Double[]{x1, x2, x3};
        Arrays.sort(roots, Collections.reverseOrder());
        return roots[0] + ", " + roots[1] + ", " + roots[2];
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (Double.compare(a1, a2) != 0) {
            return 0f;
        }
        return (float) (Math.abs(b2 - b1) / Math.sqrt(Math.pow(a1, 2) + 1));
    }

    /**
     * Даны три точки в пространстве (x1, y1, z1) , (x2, y2, z2) и (x3, y3, z3). Вам нужно найти Z координату
     * четвертой точки (x4, y4, z4), которая находится на той же плоскости что и первые три.
     * (0, 0, 1,
     * 1, 1, 1,
     * 10, 100, 1,
     * 235, -5) -> 1
     */
    // Вычисляется уравнение плоскости и посдтавляются x4 и y4.
    public static double surfaceFunction(int x1, int y1, int z1,
                                         int x2, int y2, int z2,
                                         int x3, int y3, int z3,
                                         int x4, int y4) {
        double a = (double) y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);
        double b = (double) z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);
        double c = (double) x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
        double d = -(x1 * (y2 * z3 - y3 * z2) + x2 * (y3 * z1 - y1 * z3) + x3 * (y1 * z2 - y2 * z1));
        return -(a * x4 + b * y4 + d) / c;
    }
}
