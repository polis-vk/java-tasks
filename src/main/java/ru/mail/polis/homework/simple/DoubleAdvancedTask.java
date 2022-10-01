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
        /*
         * Решал с помощью тригонометрического уравнения Виета
         */
        double aCoef = (double) b / a;
        double bCoef = (double) c / a;
        double cCoef = (double) d / a;
        double q = (Math.pow(aCoef, 2.) - 3 * bCoef) / 9;
        double r = (2 * Math.pow(aCoef, 3.) - 9 * aCoef * bCoef + 27 * cCoef) / 54;
        double s = Math.pow(q, 3.) - Math.pow(r, 2.);
        double angle;
        double x1;
        double x2;
        double x3;

        if (s > 0) {
            angle = Math.acos(r / Math.pow(q, 3. / 2.)) / 3;
            x1 = -2 * Math.sqrt(q) * Math.cos(angle) - aCoef / 3;
            x2 = -2 * Math.sqrt(q) * Math.cos(angle + 2 * Math.PI / 3) - aCoef / 3;
            x3 = -2 * Math.sqrt(q) * Math.cos(angle - 2 * Math.PI / 3) - aCoef / 3;
        } else {
            x1 = -2 * Math.cbrt(r) - aCoef / 3;
            x2 = Math.cbrt(r) - aCoef / 3;
            x3 = x2;
        }

        double min = Math.min(Math.min(x1, x2), x3);
        double max = Math.max(Math.max(x1, x2), x3);
        double mid = x1 + x2 + x3 - min - max;

        return max + ", " + mid + ", " + min;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return (Double.compare(a1, a2) == 0) ? (float) (Math.abs(b1 - b2) / Math.sqrt((1 + a1 * a1))) : 0.f;
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
        double a11 = x4 - x1;
        double a12 = y4 - y1;
        double a21 = x2 - x1;
        double a22 = y2 - y1;
        double a23 = z2 - z1;
        double a31 = x3 - x1;
        double a32 = y3 - y1;
        double a33 = z3 - z1;

        return -(a11 * a22 * a33 + a12 * a23 * a31 - a12 * a21 * a33 - a11 * a23 * a32) / (a21 * a32 - a22 * a31) + z1;
    }
}
