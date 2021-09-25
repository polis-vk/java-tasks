package ru.mail.polis.homework.simple;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.IntStream;

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
    public static double binSearch(double left, double right, int[] coefficients) {
        while (true) {
            double mid = (left + right) / 2;
            double func = coefficients[0] * Math.pow(mid, 3) + coefficients[1] * mid * mid
                    + coefficients[2] * mid + coefficients[3];
            if (func == 0) {
                return mid;
            }
            if (func < 0) {
                left = mid;
            } else {
                right = mid;
            }
        }
    }

    public static double[] methodGor(double x1, int[] coefficients) {
        double[] newCoefficients = new double[3];
        newCoefficients[0] = coefficients[0];
        newCoefficients[1] = x1 * newCoefficients[0] + coefficients[1];
        newCoefficients[2] = x1 * newCoefficients[1] + coefficients[2];
        return newCoefficients;
    }

    public static double[] quadratic(double[] coefficients, double x1) {
        double[] finalResult = new double[3];
        double dis = Math.pow(coefficients[1], 2)
                - 4 * coefficients[0] * coefficients[2];
        if (dis == 0) {
            finalResult[0] = (-1 * coefficients[1]) / (2 * coefficients[0]);
            finalResult[1] = finalResult[0];
            finalResult[2] = x1;
            Arrays.sort(finalResult);
            return finalResult;
        }
        finalResult[0] = (-1 * coefficients[1] + Math.sqrt(dis)) / (2 * coefficients[0]);
        finalResult[1] = (-1 * coefficients[1] - Math.sqrt(dis)) / (2 * coefficients[0]);
        finalResult[2] = x1;
        Arrays.sort(finalResult);

        return finalResult;
    }

    public static String equation(int a, int b, int c, int d) {
        double x1;
        double x2;
        double x3;
        int[] coefficients = {a, b, c, d};

        if (a < 0) {
            coefficients = IntStream.of(coefficients).map(i -> i * a).toArray();
        }

        double boarder = Math.pow(IntStream.of(coefficients).map(Math::abs).max().orElse(0), 3);
        x1 = binSearch(-1 * boarder, boarder, coefficients);
        double[] newCoefficients = methodGor(x1, coefficients);
        double[] results = quadratic(newCoefficients, x1);

        x1 = results[2];
        x2 = results[1];
        x3 = results[0];

        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (a1 != a2) {
            return 0;
        }

        return (float) (Math.abs(b2 - b1) / Math.sqrt(a1 * a1 + 1));
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
        int xCoefficient = (y2 - y1) * (z3 - z1) - (z2 - z1) * (y3 - y1);
        int yCoefficient = -1 * ((x2 - x1) * (z3 - z1) - (z2 - z1) * (x3 - x1));
        int zCoefficient = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);
        int d = -1 * yCoefficient * y1 - xCoefficient * x1 - zCoefficient * z1;

        return -1 * ((double) x4 * xCoefficient + y4 * yCoefficient + d) / zCoefficient;
    }
}
