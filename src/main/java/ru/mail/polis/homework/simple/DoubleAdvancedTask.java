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

        double p = (3.0 * a * c - Math.pow(b, 2)) / (3 * Math.pow(a, 2));
        double q = (2 * Math.pow(b, 3) - 9.0 * a * b * c + 27 * Math.pow(a, 2) * d) / (27 * Math.pow(a, 3));

        double discriminant = Math.pow(q / 2, 2) + Math.pow(p / 3, 3);
        double[] result = {0, 0, 0};
        if (discriminant < 0) {
            double anglePhi = Math.atan(Math.sqrt(-discriminant) / (-q / 2));

            if (q > 0) {
                anglePhi += Math.PI;
            } else if (q == 0) {
                anglePhi = Math.PI / 2;
            }

            for (int i = 0; i < 5; i += 2) {
                result[i / 2] = 2 * Math.sqrt(-p / 3) * Math.cos(anglePhi / 3 + (i * Math.PI) / 3) - (b / (3.0 * a));
            }
        } else {
            double v = Math.pow(-q / 2 + Math.sqrt(discriminant), 1.0 / 3) +
                    Math.pow(-q / 2 - Math.sqrt(discriminant), 1.0 / 3);
            result[0] = v - (b / (3.0 * a));
            result[1] = -1.0 / 2 * v - (b / (3.0 * a));
            result[2] = result[1];
        }

        double temp = result[0];
        for (int i = 1; i < result.length; i++) {
            if (result[i] > temp) {
                result[i - 1] = result[i];
                result[i] = temp;
            }
            temp = result[i];
        }

        x1 = result[0];
        x2 = result[1];
        x3 = result[2];

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

        return (float) (Math.abs(b2 - b1) / Math.sqrt(Math.pow(a1, 2) + 1.0));
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
        double a = (y2 - y1) * (z3 - z1) - (y3 - y1) * (z2 - z1);
        double b = -((x2 - x1) * (z3 - z1) - (z2 - z1) * (x3 - x1));
        double c = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);
        double d = a * (-x1) + b * (-y1) + c * (-z1);
        return ((-a) * x4 - b * y4 - d) / c;
    }
}
