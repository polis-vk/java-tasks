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
    //При решении использовалась тригонометрическая формула Виета
    public static String equation(int a, int b, int c, int d) {
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;

        double an = (double) b / a;
        double bn = (double) c / a;
        double cn = (double) d / a;

        double q = (an * an - 3 * bn) / 9;
        double r = (2 * Math.pow(an, 3) - 9 * an * bn + 27 * cn) / 54;
        double s = Math.pow(q, 3) - Math.pow(r, 2);
        s = round(s);
        if (s > 0) {
            double f = (Math.acos(r / Math.sqrt(q * q * q))) / 3;
            x1 = -2 * Math.sqrt(q) * Math.cos(f) - an / 3;
            x2 = -2 * Math.sqrt(q) * Math.cos(f + 2 * Math.PI / 3) - an / 3;
            x3 = -2 * Math.sqrt(q) * Math.cos(f - 2 * Math.PI / 3) - an / 3;
        } else {
            x1 = -2 * Math.signum(r) * Math.sqrt(q) - an / 3;
            x2 = Math.signum(r) * Math.sqrt(q) - an / 3;
        }
        x1 = round(x1);
        x2 = round(x2);
        x3 = round(x3);
        return Math.max(Math.max(x1, x2), Math.max(x2, x3)) + ", "
                + Math.max(Math.min(x1, x2), Math.min(Math.max(x1, x2), x3)) + ", "
                + Math.min(Math.min(x1, x2), Math.min(x2, x3));
    }

    private static double round(double x) {
        return Math.round(x * 1e+14) / 1e+14;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (a1 == a2) {
            return (float) (Math.abs(b1 - b2) / Math.sqrt(a1 * a2 + 1));
        } else {
            return 0;
        }
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
        /*
         * Точка рассчитывается по формуле z4 = - (a * x4 + b * y4 + d) / c
         * a, b, c - матрицы 2х2, состоящие из векторов плоскости
         * d - сумма произведений координат x1, x2, x3 на матрицы a, b и c
         */
        int x12 = x2 - x1;
        int y12 = y2 - y1;
        int z12 = z2 - z1;

        int x13 = x3 - x1;
        int y13 = y3 - y1;
        int z13 = z3 - z1;

        int a = y12 * z13 - y13 * z12;
        int b = -(x12 * z13 - x13 * z12);
        int c = x12 * y13 - x13 * y12;
        int d = -(x1 * a + y1 * b + z1 * c);

        return -(double) (a * x4 + b * y4 + d) / c;
    }
}
