package ru.mail.polis.homework.simple;

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
        // Решал с помощью тригонометрической формулы Виета
        double A = (double) b / a;
        double B = (double) c / a;
        double C = (double) d / a;
        double Q = (Math.pow(A, 2) - 3 * B) / 9;
        double R = (2 * Math.pow(A, 3) - 9 * A * B + 27 * C) / 54;
        double phi = 0;
        if (Q > EPS) {
            phi = Math.acos(R / Math.sqrt(Math.pow(Q, 3))) / 3;
        }
        double x1 = -2 * Math.sqrt(Q) * Math.cos(phi) - A / 3;
        double x2 = -2 * Math.sqrt(Q) * Math.cos(phi + (2 * Math.PI / 3)) - A / 3;
        double x3 = -2 * Math.sqrt(Q) * Math.cos(phi - (2 * Math.PI / 3)) - A / 3;
        double max = Math.max(Math.max(x1, x2), x3);
        double min = Math.min(Math.min(x1, x2), x3);
        double mid = x1 + x2 + x3 - min - max;
        return max + ", " + mid + ", " + min;
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
        return (float) (Math.abs(b1 - b2) / Math.sqrt(a1 * a2 + 1));
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
        // Если вектора лежат в одной плоскости, то определитель матрицы A равен нулю.
        // Из этого мы получили линейное уравнение относительно одной неизвестной z4
        double a11 = x4 - x1;
        double a12 = y4 - y1;
        double a21 = x2 - x1;
        double a22 = y2 - y1;
        double a23 = z2 - z1;
        double a31 = x3 - x1;
        double a32 = y3 - y1;
        double a33 = z3 - z1;
        return z1 + (-a11 * a22 * a33 - a31 * a12 * a23 + a21 * a12 * a33 + a11 * a23 * a32)
                / (a21 * a32 - a22 * a31);
    }
}
