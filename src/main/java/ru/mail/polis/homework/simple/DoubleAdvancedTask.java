package ru.mail.polis.homework.simple;

import static java.lang.Math.*;

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
        double a1 = (double) b / a;
        double b1 = (double) c / a;
        double c1 = (double) d / a;
        double p = b1 - pow(a1, 2) / 3;
        double q = 2 * pow(a1, 3) / 27 - a1 * b1 / 3 + c1;
        double D = pow(q, 2) / 4 + pow(p, 3) / 27;
        if (abs(D) < 0.0000001) {
            D = 0;
        }
        double x1;
        double x2;
        double x3;
        if (D > 0) { // один вещественный и два комплексных корня
            double temp = exp(log(-q / 2 + sqrt(D)) / 3);
            x1 = temp - p / (3 * temp) - a1 / 3;
            x2 = -(temp - p / (3 * temp)) / 2 - a1 / 3;
            x3 = (sqrt(3) / 2) * (temp + p / (3 * temp));
        } else if (D < 0) { // один действительный корень и два ненастоящих комплексно сопряженных корня
            double acos = acos((-q / 2) * (sqrt(-27 / pow(p, 3))));
            x1 = sqrt(-4 * p / 3) * cos(acos / 3) - a1 / 3;
            x2 = -sqrt(-4 * p / 3) * cos(acos / 3 + PI / 3) - a1 / 3;
            x3 = -sqrt(-4 * p / 3) * cos(acos / 3 - PI / 3) - a1 / 3;
        } else { // D = 0; все три корня действительны, причём, по крайней мере, два из них равны
            if (p < 0) {
                x1 = 3 * q / p - a1 / 3;
                x2 = -3 * q / (2 * p) - a1 / 3;
                x3 = x2;
            } else { // p >= 0
                x1 = -a1 / 3;
                x2 = -a1 / 3;
                x3 = -a1 / 3;
            }
        }
        double sum = x1 + x2 + x3;             // <-\
        double min = min(min(x1, x2), x3);     // <----> сортировка корней
        double max = max(max(x1, x2), x3);     // <-/
        return max + ", " + (sum - min - max) + ", " + min;
    }
    // Была использована теорема Виета-Кардана.
    // Информация для случая det > 0 (35-38 строка) была взята с сайта:
    // "https://c-sharp.pro/полное-решение-кубического-уравнени/".

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return (a1 != a2) ? 0 : (float) (abs(b2 - b1) / sqrt(1 + pow(a1, 2)));
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
        int x41 = x4 - x1;
        int y41 = y4 - y1;
        int x21 = x2 - x1;
        int y21 = y2 - y1;
        int z21 = z2 - z1;
        int x31 = x3 - x1;
        int y31 = y3 - y1;
        int z31 = z3 - z1;
        int firstMinor = y21 * z31 - z21 * y31;
        int secondMinor = x21 * z31 - z21 * x31;
        int thirdMinor = x21 * y31 - y21 * x31;
        return ((double) (-x41 * (firstMinor) + y41 * (secondMinor)) / (thirdMinor)) + z1;
    }
}