package ru.mail.polis.homework.simple;

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
    public static String equation(int a, int b, int c, int d) {
        /*
          Trigonometric solution for 3 real roots
          https://en.wikipedia.org/wiki/Cubic_equation#Trigonometric_solution_for_three_real_roots

          1) Приводим к виду x^3 + bs * x^2 + cs * x + ds
          2) Приводим к виду t^3 + p * t + q = 0 (x = t - bs / 3)
          3) Находим корни t_k по тригонометрической формуле
          4) Возвращаемся к корням x_k

         */

        double bs = (double) b / a;
        double cs = (double) c / a;
        double ds = (double) d / a;

        double p = -bs * bs / 3 + cs;
        double q = 2 * bs * bs * bs / 27 - cs * bs / 3 + ds;

        double m = 2 * Math.sqrt(-p / 3);
        double n = Math.acos(3 * q / 2 / p * Math.sqrt(-3 / p)) / 3;

        double t1 = (Math.abs(m) < EPS ? 0 : m * Math.cos(n));
        double t2 = (Math.abs(m) < EPS ? 0 : m * Math.cos(n - 2 * Math.PI / 3));
        double t3 = (Math.abs(m) < EPS ? 0 : m * Math.cos(n - 4 * Math.PI / 3));

        double x1 = t1 - bs / 3;
        double x2 = t2 - bs / 3;
        double x3 = t3 - bs / 3;

        double xMax= Math.max(Math.max(x1, x2), x3);
        double xMin = Math.min(Math.min(x1, x2), x3);
        double xMid;

        if (xMax == x1 && xMin == x3) {
            xMid = x2;
        } else if (xMax == x1 && xMin == x2) {
            xMid = x3;
        } else {
            xMid = x1;
        }

        return xMax + ", " + xMid + ", " + xMin;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (Math.abs(a1 - a2) > EPS) {
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
        double ax = x2 - x1;
        double bx = x3 - x1;

        double ay = y2 - y1;
        double by = y3 - y1;

        double az = z2 - z1;
        double bz = z3 - z1;

        double A = ay * bz - az * by;
        double B = -(ax * bz - az * bx);
        double C = ax * by - ay * bx;

        double D = -A * x1 + -B * y1 + -C * z1;

        return (-D - A * x4 - B * y4) / C;
    }
}
