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
    //use general cubic formula
    public static String equation(int a, int b, int c, int d) {
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;

        double delta0 = Math.pow(b, 2) - 3 * a * c;
        double delta1 = 2 * Math.pow(b, 3) - 9 * a * b * c + 27 * Math.pow(a, 2) * d;
        double c1 = Math.pow((delta0 + Math.abs(Math.pow(delta0, 2) - 4 * Math.pow(delta1, 3))) / 2.0, 1 / 3.0);
        double c2 = Math.pow((delta0 - Math.abs(Math.pow(delta0, 2) - 4 * Math.pow(delta1, 3))) / 2.0, 1 / 3.0);

        double k1 = (-1 + Math.sqrt(-3.0)) / 2.0;
        double k2 = (-1 - Math.sqrt(-3.0)) / 2.0;

        if (c1 == 0 && c2 == 0){
            x1 = (- 1.0 / 3 * a) * b;

        } else if (c1 == 0){
            x1 = (- 1.0 / 3 * a) * (b + k1 * c2 + delta0 / (k1 * c2));
            x2 = (- 1.0 / 3 * a) * (b + k2 * c2 + delta0 / (k2 * c2));
        } else {

        }







        double[] xes = {x1, x2, x3};
        Arrays.sort(xes);

        return xes[2] + ", " + xes[1] + ", " + xes[0];
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
