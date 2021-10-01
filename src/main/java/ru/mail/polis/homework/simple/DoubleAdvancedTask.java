package ru.mail.polis.homework.simple;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {

    private static final double EPS = 1E-6;

    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     * Решение методом Виета-Кардано
     */
    public static String equation(int a, int b, int c, int d) {
        double a1 = (double) b / a;
        double b1 = (double) c / a;
        double c1 = (double) d / a;
        double q = (Math.pow(a1, 2) - 3 * b1) / 9;
        double r = (2 * Math.pow(a1, 3) - 9 * a1 * b1 + 27 * c1) / 54;
        double fi = Math.acos(r / Math.pow(q, 1.5)) / 3;
        if (Math.pow(r, 2) >= Math.pow(q, 3)) {
            double e = -Math.signum(r) * Math.pow((Math.abs(r) + Math.sqrt(r * r - q * q * q)), 1.0 / 3);
            double f = (Math.abs(e) > EPS) ? q / e : 0;
            return (e + f) - a1 / 3 + ", 0, 0";
        }
        double x3 = -2 * Math.sqrt(q) * Math.cos(fi) - a1 / 3;
        double x2 = -2 * Math.sqrt(q) * Math.cos(fi - 2 * Math.PI / 3) - a1 / 3;
        double x1 = -2 * Math.sqrt(q) * Math.cos(fi + 2 * Math.PI / 3) - a1 / 3;
        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (Math.abs(a1 - a2) > EPS) {
            return 0;
        }
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
        double det1 = ((y2 - y1) * (z3 - z1) - (y3 - y1) * (z2 - z1));
        double det2 = ((x2 - x1) * (z3 - z1) - (x3 - x1) * (z2 - z1));
        double det3 = ((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1));
        return z1 + ((y4 - y1) * det2 - (x4 - x1) * det1) / det3;
    }
}
