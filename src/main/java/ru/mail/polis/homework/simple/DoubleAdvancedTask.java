package ru.mail.polis.homework.simple;

import java.util.Arrays;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {

    private static final double EPS = 1e-10;

    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */
    public static String equation(int a, int b, int c, int d) {
        //Тригонометрическая формула Виета https://math.fandom.com/ru/wiki/%D0%A2%D1%80%D0%B8%D0%B3%D0%BE%D0%BD%D0%BE%D0%BC%D0%B5%D1%82%D1%80%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B0%D1%8F_%D1%84%D0%BE%D1%80%D0%BC%D1%83%D0%BB%D0%B0_%D0%92%D0%B8%D0%B5%D1%82%D0%B0
        double x1 = b * 1. / a;
        double x2 = c * 1. / a;
        double x3 = d * 1. / a;
        //Вычисляем Q, R, S и F
        double q = (Math.pow(x1, 2) - 3 * x2) / 9;
        double r = (2 * Math.pow(x1, 3) - 9 * x1 * x2 + 27 * x3) / 54;
        double s = Math.pow(q, 3) - Math.pow(r, 2);
        //Находим корни
        return calcVietaFormula(q, r, s, x1);
    }

    private static String calcVietaFormula(double q, double r, double s, double x1) {
        double[] results = new double[3];

        if (s > 0) {
            double f = Math.acos(r / Math.pow(q, 1.5)) / 3;
            results[0] = -2 * Math.sqrt(q) * Math.cos(f) - x1 / 3;
            results[1] = -2 * Math.sqrt(q) * Math.cos(f + 2 * Math.PI / 3) - x1 / 3;
            results[2] = -2 * Math.sqrt(q) * Math.cos(f - 2 * Math.PI / 3) - x1 / 3;
        } else {
            results[0] = Math.cbrt(r) - x1 / 3;
            results[1] = results[0];
            results[2] = -2 * Math.cbrt(r) - x1 / 3;
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
        return (float) (Math.abs(a1 - a2) > EPS ? 0 : Math.abs(b1 - b2) / Math.sqrt(a1 * a1 + 1));
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
        double a = z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);
        double b = y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);
        double c = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
        double d = x1 * (y2 * z3 - y3 * z2) + x2 * (y3 * z1 - y1 * z3) + x3 * (y1 * z2 - y2 * z1);

        return (d - b * x4 - a * y4) / c;
    }
}
