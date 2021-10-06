package ru.mail.polis.homework.simple;

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

    // Тригонометрическая формула Виета
    public static String equation(int a, int b, int c, int d) {

        double a1 = (double) b / a;
        double b1 = (double) c / a;
        double c1 = (double) d / a;

        double q = (Math.pow(a1, 2) - 3 * b1) / 9;
        double r = (2 * Math.pow(a1, 3) - 9 * a1 * b1 + 27 * c1) / 54;
        double s = Math.pow(q, 3) - Math.pow(r, 2);

        double x1;
        double x2;
        double x3;

        if (s > 0) {
            double phi = Math.acos(r / Math.sqrt(Math.pow(q, 3))) / 3;
            x1 = -2 * Math.sqrt(q) * Math.cos(phi) - a1 / 3;
            x2 = -2 * Math.sqrt(q) * Math.cos(phi + 2 * Math.PI / 3) - a1 / 3;
            x3 = -2 * Math.sqrt(q) * Math.cos(phi - 2 * Math.PI / 3) - a1 / 3;
        } else {
            x1 = -2 * Math.cbrt(r) - a1 / 3;
            x2 = x3 = Math.cbrt(r) - a1 / 3;
        }

        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return (a1 - a2) == 0 ? 0 : (float) (Math.abs(b1 - b2) / Math.sqrt(a1 * a2 + 1));
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

        int[] x = {x2 - x1, x3 - x1, x4 - x1};
        int[] y = {y2 - y1, y3 - y1, y4 - y1};
        int[] z = {z2 - z1, z3 - z1};

        return (double) (y[2] * x[0] * z[1] + y[1] * x[2] * z[0] - x[2] * y[0] * z[1] - x[1] * y[2] * z[0])
                / (x[0] * y[1] - y[0] * x[1]) + z1;
    }
}
