package ru.mail.polis.homework.simple;

import java.util.Arrays;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {

    /**
     * Вывести три корня кубического уравнения через запятую: ax ^ 3 + bx ^ 2 + cx + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */
    //use Кардано-Виета
    public static String equation(int a, int b, int c, int d) {
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;

        double a_ = b * 1.0 / a;
        double b_ = c * 1.0 / a;
        double c_ = d * 1.0 / a;

        if (b == 0 && c == 0){
            return Math.pow((c_), 1/3.0)+ ", " + 0 + ", " + 0;
        }

        double Q = (Math.pow(a_, 2) - 3 * b_) / 9;
        double R = (2 * Math.pow(a_, 3) - 9 * a_ * b_ + 27 * c_) / 54;
        double S = Math.pow(Q, 3) - Math.pow(R, 2);
        double phi = (Math.acos(R / Math.pow(Q, 1.5))) / 3;
        x1 = -2 * Math.pow(Q, 0.5) * Math.cos(phi) - a_ / 3;
        x2 = -2 * Math.pow(Q, 0.5) * Math.cos(phi + (2 * Math.PI / 3)) - a_ / 3;
        x3 = -2 * Math.pow(Q, 0.5) * Math.cos(phi - (2 * Math.PI / 3)) - a_ / 3;

        x1 = closeToInt(x1) ? Math.round(x1) : x1;
        x2 = closeToInt(x2) ? Math.round(x2) : x2;
        x3 = closeToInt(x3) ? Math.round(x3) : x3;

        double[] xes = {x1, x2, x3};
        Arrays.sort(xes);

        return xes[2] + ", " + xes[1] + ", " + xes[0];
    }

    public static boolean closeToInt(double val) {
        double errMargin = 0.00000001;
        return Math.abs(Math.round(val) - val) <= errMargin;
    }


    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (a1 == a2) {
            return (float) (Math.abs(b2 - b1) / Math.sqrt(Math.pow(a1, 2) + 1));
        } else {
            return 0f;
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
        //vector PQ
        double a1 = x2 - x1;
        double b1 = y2 - y1;
        double c1 = z2 - z1;
        //vector PR
        double a2 = x3 - x1;
        double b2 = y3 - y1;
        double c2 = z3 - z1;

        //умножение векторов
        double a = b1 * c2 - b2 * c1;
        double b = a2 * c1 - a1 * c2;
        double c = a1 * b2 - b1 * a2;
        double d = (-a * x1 - b * y1 - c * z1);

        // a*x + b*y + c*z + d = 0
        return (-a * x4 - b * y4 - d) / c;
    }
}
