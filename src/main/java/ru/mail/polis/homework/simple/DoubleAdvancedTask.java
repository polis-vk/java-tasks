package ru.mail.polis.homework.simple;

import java.util.Arrays;


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

    public static String equation(int a, int b, int c, int d) { // метод Кардана
        double[] answersX = new double[3];

        double convertEquationToX = -b / (double) (3 * a); // преобразование уравнение в обратный вид

        double p = ((3 * a * c) - Math.pow(b, 2)) / (3 * Math.pow(a, 2));
        double q = ((2 * Math.pow(b, 3)) - (9 * a * b * c) + (27 * Math.pow(a, 2) * d)) / (27 * Math.pow(a, 3));
        double D = Math.pow((q / 2), 2) + Math.pow((p / 3), 3); // дискриминант

        if (D < 0) {
            double angle = Math.PI / 2; // при условии что q = 0
            if (q < 0) {
                angle = Math.atan((Math.sqrt(-D)) / (-q / 2));
            } else if (q > 0) {
                angle = Math.atan((Math.sqrt(-D)) / (-q / 2)) + Math.PI;
            }
            // возравращаемся от y к x
            answersX[0] = 2 * Math.sqrt((-p / 3)) * Math.cos(angle / 3) + convertEquationToX;
            answersX[1] = 2 * Math.sqrt((-p / 3)) * Math.cos((angle + (2 * Math.PI)) / 3) + convertEquationToX;
            answersX[2] = 2 * Math.sqrt((-p / 3)) * Math.cos((angle + (4 * Math.PI)) / 3) + convertEquationToX;
        } else {
            answersX[0] = 2 * Math.cbrt((-q / 2)) + convertEquationToX;
            answersX[1] = -Math.cbrt((-q / 2)) + convertEquationToX;
        }

        Arrays.sort(answersX); // для вывода
        return answersX[2] + ", " + answersX[1] + ", " + answersX[0];
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */

    public static float length(double a1, double b1, double a2, double b2) { // если прямые непараллельны  - 0
        return (Math.abs(a1 - a2) <= EPS) ? (float) (Math.abs(b1 - b2) / Math.sqrt(1 + Math.pow(a1, 2))) : 0;
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

        // выразить определитель
        int vecX1 = x2 - x1;
        int vecY1 = y2 - y1;
        int vecZ1 = z2 - z1;
        int vecX2 = x3 - x1;
        int vecY2 = y3 - y1;
        int vecZ2 = z3 - z1;
        int vecX3 = x4 - x1;
        int vecY3 = y4 - y1;


        return  (((vecX1 * vecY3 - vecY1 * vecX3) * vecZ2 - vecZ1 * (vecX2 * vecY3 - vecX3 * vecY2)) / (double) (vecX1 * vecY2 - vecY1 * vecX2)) + z1;
    }
}
