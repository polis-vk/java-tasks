package ru.mail.polis.homework.simple;

import java.util.ArrayList;
import java.util.Collections;

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
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;

        ArrayList<Double> answersList = new ArrayList<>();
        if (d == 0) { //вынос x за скобки + решение квадратного уравнения
            answersList.add(0d);
            double discriminant = Math.pow(b, 2) - 4 * a * c;
            if (discriminant >= 0) {
                answersList.add((-b + Math.sqrt(discriminant)) / (2 * a));
                answersList.add((-b - Math.sqrt(discriminant)) / (2 * a));
            }
            else {
                answersList.add(0d);
                answersList.add(0d);
            }
        }
        else { // решение методом Виета - Кардано

            // приводим уравнение к виду x^3+ax^2+bx+c = 0
            double newA = (double) b / a;
            double newB = (double) c / a;
            double newC = (double) d / a;

            double Q = (Math.pow(newA, 2) - 3 * newB) / 9d;
            double R = (2 * Math.pow(newA, 3) - 9 * newA * newB + 27 * newC) / 54d;
            double S = Math.pow(Q, 3) - Math.pow(R, 2);
            if (S > 0) {
                double t = Math.acos(R/Math.sqrt(Math.pow(Q, 3))) / 3d;
                x1 = -2 * Math.sqrt(Q) * Math.cos(t) - newA / 3d;
                x2 = -2 * Math.sqrt(Q) * Math.cos(t + (2 * Math.PI / 3d)) - newA / 3d;
                x3 = -2 * Math.sqrt(Q) * Math.cos(t - (2 * Math.PI / 3d)) - newA / 3d;
            }
            answersList.add(x1);
            answersList.add(x2);
            answersList.add(x3);
        }

        Collections.sort(answersList);
        Collections.reverse(answersList);
        x1 = answersList.get(0);
        x2 = answersList.get(1);
        x3 = answersList.get(2);

        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        //используем уравнение прямой y = kx + b
        double eps = 0.000001;
        if (equalsDoubles(a1, a2, eps)) { // если k1 = k2, то прямые параллельны
            return (float) (Math.abs(b2 - b1) / Math.sqrt(Math.pow(a1, 2) + 1));
        }
        return 0;
    }

    private static boolean equalsDoubles(double a, double b, double eps) {
        return Math.abs(a - b) <= eps;
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
        //находим кооэффициенты для уравнения плоскости
        double A = y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);
        double B = z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);
        double C = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
        double D = -(x1 * (y2 * z3 - y3 * z2) + x2 * (y3 * z1 - y1 * z3) + x3 * (y1 * z2 - y2 * z1));

        //A * x4 + B * y4 + C * z4 + D = 0
        //выражаем из уравнения z4
        return -((A * x4 + B * y4 + D) / C);
    }
}
