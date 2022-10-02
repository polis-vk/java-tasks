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
    public static String equation(int a, int b, int c, int d) {
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;

        double a1 = b / (double) a;
        double b1 = c / (double) a;
        double c1 = d / (double) a;
        double f = 0;

        double q = (3 * b1 - Math.pow(a1, 2)) / 9.0;
        double r = (9 * a1 * b1 - 2 * Math.pow(a1, 3) - 27 * c1) / 54.0;
        double s = Math.pow(q, 3) + Math.pow(r, 2);

        if (s < 0) {
            f = 1 / 3.0 * (Math.acos(r / Math.sqrt(-Math.pow(q, 3))));
            double xBase1 = 2 * Math.sqrt(-q) * Math.cos(f) - a1 / 3.0;
            double xBase2 = 2 * Math.sqrt(-q) * Math.cos(f + Math.PI * 2 / 3) - a1 / 3.0;
            double xBase3 = 2 * Math.sqrt(-q) * Math.cos(f - Math.PI * 2 / 3) - a1 / 3.0;

            double[] array = new double[3];
            array[0] = xBase1;
            array[1] = xBase2;
            array[2] = xBase3;

            int index1 = 0;
            int index2 = 0;
            int index3 = 0;

            for (int i = 0; i < array.length; i++) {
                if (x1 < array[i]) {
                    x1 = array[i];
                    index1 = i;
                }
                if (x3 > array[i]) {
                    x3 = array[i];
                    index3 = i;
                }
            }

            for (int i = 0; i < array.length; i++) {
                if ((i != index1) && (i != index3)) {
                    x2 = array[i];
                }
            }

            return x1 + ", " + x2 + ", " + x3;
        }

        double xBase1 = 2 * Math.pow(r, 1 / 3.0) - a1 / 3.0;
        double xBase2 = -Math.pow(r, 1 / 3.0) - a1 / 3.0;

        if (xBase1 > xBase2) {
            x1 = xBase1;
            x2 = xBase2;
            x3 = x2;
        }
        else {
            x1 = xBase2;
            x2 = xBase1;
            x3 = x2;
        }

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

        double d = Math.sqrt(Math.pow((b1 * a1 - b2 * a2) / (a1 * a2 + 1), 2) + Math.pow(((b2 - b1) / (a1 * a2 + 1)), 2));
        return (float) d;
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
        double a = (y2 - y1) * (z3 - z1) - (z2 - z1) * (y3 - y1);
        double b = (x2 - x1) * (z3 - z1) - (z2 - z1) * (x3 - x1);
        double c = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);

        double z4 = (a * x1 - b * y1 + c * z1 - a * x4 + b * y4) / c;

        return z4;
    }
}
