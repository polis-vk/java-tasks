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
    public static String equation(int a, int b, int c, int d) {  //Vieta's formulas
        double x1;
        double x2;
        double x3;

        double[] dirtyRoots = new double[3];

        double ma = (double) b / a;
        double mb = (double) c / a;
        double mc = (double) d / a;
        double q = (Math.pow(ma, 2.0) - 3.0 * mb) / 9.0;
        double r = (2.0 * Math.pow(ma, 3.0) - 9.0 * ma * mb + 27.0 * mc) / 54.0;
        double t = Math.acos(r / Math.sqrt(Math.pow(q, 3.0))) / 3.0;
        dirtyRoots[0] = (-2.0 * Math.sqrt(q) * Math.cos(t) - ma / 3.0);
        dirtyRoots[1] = (-2.0 * Math.sqrt(q) * Math.cos(t + (2.0 * Math.PI / 3.0)) - ma / 3.0);
        dirtyRoots[2] = (-2.0 * Math.sqrt(q) * Math.cos(t - (2.0 * Math.PI / 3.0)) - ma / 3.0);

        Arrays.sort(dirtyRoots);
        dirtyRoots = reverseDoubleArray(dirtyRoots);
        x1 = ifNanWrapToZero(dirtyRoots[0]);
        x2 = ifNanWrapToZero(dirtyRoots[1]);
        x3 = ifNanWrapToZero(dirtyRoots[2]);

        return x1 + ", " + x2 + ", " + x3;
    }

    public static double ifNanWrapToZero(double val) {
        return Double.isNaN(val) ? 0 : val;
    }

    public static double[] reverseDoubleArray(double[] numbers) {
        double[] res = new double[3];
        res[0] = numbers[2];
        res[1] = numbers[1];
        res[2] = numbers[0];
        return res;
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
        return (float) ((Math.abs(b1 - b2)) / (Math.sqrt(Math.pow(a1, 2) + 1)));
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

        double detX = determinantOfTwoByTwoMatrix(new double[][]{{y2 - y1, z2 - z1}, {y3 - y1, z3 - z1}});
        double detY = determinantOfTwoByTwoMatrix(new double[][]{{x2 - x1, z2 - z1}, {x3 - x1, z3 - z1}});
        double detZ = determinantOfTwoByTwoMatrix(new double[][]{{x2 - x1, y2 - y1}, {x3 - x1, y3 - y1}});
        double tail = -x1 * detX + y1 * detY - z1 * detZ; //now detX equals A, detY equals B, detZ equals C and tail equals D in formula Ax+By+Cz+D=0
        return (-tail + y4 * detY - x4 * detX) / (detZ);


    }

    public static double determinantOfTwoByTwoMatrix(double[][] matrix) {
        if (matrix.length == matrix[0].length
                && matrix.length == matrix[1].length
                && matrix.length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else {
            throw new IllegalArgumentException();
        }
    }
}
