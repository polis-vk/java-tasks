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
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */
    public static String equation(int a, int b, int c, int d) {
        /**
         * В данном решении применяется
         * Формула Кардано для решения кубических уравнений
         * https://ru.intemodino.com/math/algebra/equations/cardano's-formula-for-solving-cubic-equations.html
         */
        double[] results = new double[3];
        final double REVERSE_REPLACEMENT_FROM_Y_TO_X = (b / (double) (3 * a));
        //после приведения к уравнению:
        //y ^ 3 + P * y + Q = 0
        //коэффициенты равны
        final double P = (3 * a * c - Math.pow(b, 2)) / (3 * Math.pow(a, 2));
        final double Q = ((2 * Math.pow(b, 3) - 9 * a * b * c + 27 * Math.pow(a, 2) * d)) / (27 * Math.pow(a, 3));
        final double DISCRIMINANT = Math.pow((Q / 2), 2) + Math.pow((P / 3), 3);

        if (DISCRIMINANT < 0) {
            double angle = 0;
            if (Q == 0) {
                angle = Math.PI / 2;
            } else {
                final double GENERAL_ARCTANGENT = Math.atan(Math.sqrt(-DISCRIMINANT) / (-Q / 2));
                if (Q < 0) {
                    angle = GENERAL_ARCTANGENT;
                } else {
                    angle = GENERAL_ARCTANGENT + Math.PI;
                }
            }
            double y1 = 2 * Math.sqrt(-P / 3) * Math.cos(angle / 3);
            double y2 = 2 * Math.sqrt(-P / 3) * Math.cos(angle / 3 + 2 * Math.PI / 3);
            double y3 = 2 * Math.sqrt(-P / 3) * Math.cos(angle / 3 + 4 * Math.PI / 3);

            //обратная замена
            results[0] = y1 - REVERSE_REPLACEMENT_FROM_Y_TO_X;
            results[1] = y2 - REVERSE_REPLACEMENT_FROM_Y_TO_X;
            results[2] = y3 - REVERSE_REPLACEMENT_FROM_Y_TO_X;
        } else if (Math.abs(DISCRIMINANT) <= 1e-10) {
            double y1 = 2 * Math.cbrt(-Q / 2);
            double y2 = -Math.cbrt(-Q / 2);
            //обратная замена
            results[0] = y1 - REVERSE_REPLACEMENT_FROM_Y_TO_X;
            results[1] = y2 - REVERSE_REPLACEMENT_FROM_Y_TO_X;
            results[2] = results[1];
        }
        Arrays.sort(results);
        return results[2] + ", " + results[1] + ", " + results[0];
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        final double COEFFICIENT_AT_Y = 1D;//1*y = a*x+b
        if (a1 == a2) {//если прямые параллельны, то считаем расстояние
            double result = Math.abs(b1 - b2) / Math.sqrt(COEFFICIENT_AT_Y + Math.pow(a1, 2));
            return (float) result;
        }
        return 0F;
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
        /**
         * Составим уравнение плоскости по векторам A{x2-x1, y2-y1, z2-z1}, B{x3-x1, y3-y1, z3-z1} и C{x4-x1, y4-y1, z4-z1}
         * формат имени переменных: <Название вектора><Координата>
         * Пример:
         * Ax, Ay, Az, Bx, By, Bz ...
         * Соответсвенно переменная Ax равна значению вектора A по координате X.(x2-x1)
         */
        final int A_X = x2 - x1;
        final int A_Y = y2 - y1;
        final int A_Z = z2 - z1;
        final int B_X = x3 - x1;
        final int B_Y = y3 - y1;
        final int B_Z = z3 - z1;
        final int C_X = x4 - x1;
        final int C_Y = y4 - y1;
        /**
         * Определитель матрицы по этим переменным должен быть равен нулю.
         * Сам определитель:
         * |A_X  A_Y  A_Z|
         * |B_X  B_Y  B_Z|
         * |C_X  C_Y  z4 - z1|
         * Расскрыв определитель получим(все расчёты произведены на бумажке):
         * z4 = z1 + (((A_X * C_Y - A_Y * C_X) * B_Z - A_Z * (B_X * C_Y - C_X * B_Y)) / (A_X * B_Y - A_Y * B_X))
         */
        return z1 + (((A_X * C_Y - A_Y * C_X) * B_Z - A_Z * (B_X * C_Y - C_X * B_Y)) / (double) (A_X * B_Y - A_Y * B_X));
    }
}
