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
    public static String equation(int a, int b, int c, int d) {
        double x1;
        double x2;
        double x3;

        // Решал через тригонометрическую формулу Виета

        double r = (double) b / a;
        double s = (double) c / a;
        double t = (double) d / a;

        double p = (3 * s - Math.pow(r, 2)) / 3;
        double q = 2 * Math.pow(r, 3) / 27 - r * s / 3 + t;

        double D = Math.pow((p / 3), 3) + Math.pow((q / 2), 2);

        double first;
        double second;
        double third;

        if (D < 0) {
            double ro = Math.sqrt((-Math.pow(p, 3)) / 27);
            double fi = Math.acos(-q / (2 * ro));

            first = 2 * Math.pow(ro, 1.0 / 3) * Math.cos(fi / 3) - r / 3;
            second = 2 * Math.pow(ro, 1.0 / 3) * Math.cos(fi / 3 + 2 * Math.PI / 3) - r / 3;
            third = 2 * Math.pow(ro, 1.0 / 3) * Math.cos(fi / 3 + 4 * Math.PI / 3) - r / 3;
        } else {
            double alpha = Math.pow((-q / 2 + Math.sqrt(D)), 1.0 / 3);
            double beta = Math.pow((-q / 2 - Math.sqrt(D)), 1.0 / 3);

            first = alpha + beta - r / 3;
            second = -(alpha + beta) / 2 - r / 3;
            third = second;
        }

        x1 = Math.max(first, Math.max(second, third));
        x3 = Math.min(first, Math.min(second, third));
        x2 = first + second + third - x1 - x3;

        return x1 + ", " + x2 + ", " + x3;
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
        return (float) (Math.abs(b1 - b2) / Math.sqrt(a1 * a1 + 1));
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
        // counting value of each position in determinant except a31 (because a31 = z4 - z1)
        int a11 = x4 - x1;
        int a12 = x2 - x1;
        int a13 = x3 - x1;

        int a21 = y4 - y1;
        int a22 = y2 - y1;
        int a23 = y3 - y1;

        int a32 = z2 - z1;
        int a33 = z3 - z1;

        // determinant = 0, count it and find z4

        return (((double) a12 * a21 * a33 + a11 * a23 * a32 - a11 * a22 * a33 - a13 * a21 * a32)
                / (a12 * a23 - a13 * a22)) + z1;
    }
}
