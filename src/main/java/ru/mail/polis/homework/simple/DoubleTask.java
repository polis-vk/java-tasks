package ru.mail.polis.homework.simple;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleTask {

    /**
     * Вывести два корня квадратного уравнения через запятую: a * x ^ 2 + b * x + c = 0;
     * Вывод менять не нужно, надо только посчитать x1 и x2, где x1 > x2
     * Пример: (1, 5, 4) -> "-1.0, -4.0"
     */
    public static String equation(int a, int b, int c) {
        double x1 = 0;
        double x2 = 0;
        if (a == 0 && c != 0 && b != 0) {
            x1 = -((double) c /b);
            x2 = x1;
        } else if (b == 0 && c != 0) {
            x1 = Math.sqrt(-(double) c /a);
            x2 -= x1;
        } else if (c == 0 && b != 0) {
            double current = -(double) b /a;
            x1 = Math.max(current, 0);
            x2 = Math.min(current, 0);
        } else if (c != 0) {
            double d = Math.pow(b, 2) - 4 * a * c;
            if (d > 0) {
                x1 = (a > 0 ? (-b + Math.sqrt(d)) : (-b - Math.sqrt(d))) / (2 * a);
                x2 = (a > 0 ? (-b - Math.sqrt(d)) : (-b + Math.sqrt(d))) / (2 * a);
            } else if (d == 0) {
                x1 = (double) -b / 2 * a;
                x2 = x1;
            }
        }
        return x1 + ", " + x2;
    }

    /**
     * Нужно посчитать расстояние, между двумя точками (x1, y1) и (x2, y2)
     * Пример: (0, 0, 3, 4) -> 5.0
     */
    public static float length(double x1, double y1, double x2, double y2) {
        return (float) Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
