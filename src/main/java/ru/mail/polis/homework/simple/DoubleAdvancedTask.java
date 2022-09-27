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

    /**
     * Тригонометрическая формула Виета
     */

    public static String equation(int a, int b, int c, int d) {
        double newa = b == 0 ? 0 : (double) b / a;
        double newb = c == 0 ? 0 : (double) c / a;
        double newc = d == 0 ? 0 : (double) d / a;
        double Q = (newa * newa - 3 * newb) / 9;
        double R = (2 * newa * newa * newa - 9 * newa * newb + 27 * newc) / 54;
        double S = Q * Q * Q - R * R;
        double fi;
        double x1;
        double x2;
        double x3;
        if (S > 0) {
            fi = Math.acos(R / Math.sqrt(Q * Q * Q)) / 3;
            x1 = -2 * Math.sqrt(Q) * Math.cos(fi) - newa / 3;
            x2 = -2 * Math.sqrt(Q) * Math.cos(fi + 2 * Math.PI / 3) - newa / 3;
            x3 = -2 * Math.sqrt(Q) * Math.cos(fi - 2 * Math.PI / 3) - newa / 3;
        } else {
            x1 = -2 * Math.cbrt(R) - newa / 3;
            x2 = Math.cbrt(R) - newa / 3;
            x3 = x2;
        }
        double buf;
        if (x1 < x2) {
            buf = x1;
            x1 = x2;
            x2 = buf;
        }
        if (x2 < x3) {
            buf = x2;
            x2 = x3;
            x3 = buf;
        }
        if (x1 < x2) {
            buf = x1;
            x1 = x2;
            x2 = buf;
        }
        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return 0;
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
        double ni = (y2 - y1) * (z3 - z1) - (y3 - y1) * (z2 - z1);
        double nj = (x2 - x1) * (z3 - z1) - (x3 - x1) * (z2 - z1);
        double nk = (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1);
        return (ni * (x4 - x1) + -nj * (y4 - y1)) / -nk + z1;
    }
}
