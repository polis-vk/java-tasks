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
        if (b == 0 && c == 0 && d == 0) {
            return "0, 0, 0";
        }
        if (b == 0 && c == 0) {
            double root = Math.pow(((double) d) / ((double) a), 1.0 / 3.0);
            return root + ", " + root + ", " + root;
        }
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;
        if (d == 0) {
            int discriminant = b ^ 2 - 4 * a * c;
            if (discriminant > 0) {
                x2 = (-b + Math.sqrt(discriminant)) / (2 * a);
                x3 = (-b - Math.sqrt(discriminant)) / (2 * a);
            }
            if (discriminant == 0) {
                x1 = ((double) -b) / (2 * a);
            }
        } else {
            double[] result = cardano(a, b, c, d);
            x1 = result[0];
            x2 = result[1];
            x3 = result[2];
        }
        if (x3 < x1) {
            double temp = x1;
            x1 = x3;
            x3 = temp;
        }
        if (x2 < x1) {
            double temp = x1;
            x1 = x2;
            x2 = temp;
        }
        if (x3 < x2) {
            double temp = x2;
            x2 = x3;
            x3 = temp;
        }
        return x1 + ", " + x2 + ", " + x3;
    }

    private static double[] cardano(double a, double b, double c, double d) {
        double[] result = new double[3];
        if (d != 1.0) {
            a = a / d;
            b = b / d;
            c = c / d;
        }
        double p = b / 3 - a * a / 9;
        double q = a * a * a / 27 - a * b / 6 + c / 2;
        double discriminant = p * p * p + q * q;
        if (discriminant > 0) {
            double r = Math.pow(-q + Math.sqrt(discriminant), 1.0 / 3.0);
            double s = Math.pow(-q - Math.sqrt(discriminant), 1.0 / 3.0);
            result[0] = r + s - a / 3;
        } else if (discriminant == 0) {
            result[0] = 2 * Math.pow(-q, 1.0 / 3.0) - a / 3;
            result[1] = -Math.pow(-q, 1.0 / 3.0) - a / 3;
        } else {
            double ang = Math.acos(-q / Math.sqrt(-p * p * p));
            double t = 2 * Math.sqrt(-p);
            for (int k = -1; k <= 1; k++) {
                double theta = (ang - 2 * Math.PI * k) / 3;
                result[k + 1] = t * Math.cos(theta) - a / 3;
            }
        }
        return result;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return (a2 - a1 != 0) ? 0 : (float) (Math.abs(b2 - b1) / Math.sqrt(1 + Math.pow(a1, 2)));
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
        int a1 = x2 - x1;
        int b1 = y2 - y1;
        int c1 = z2 - z1;
        int a2 = x3 - x1;
        int b2 = y3 - y1;
        int c2 = z3 - z1;
        // Коэффициенты для уравнения плоскости
        int a = b1 * c2 - b2 * c1;
        int b = a2 * c1 - a1 * c2;
        int c = a1 * b2 - b1 * a2;
        int d = (-a * x1 - b * y1 - c * z1);
        return (double) -(a * x4 + b * y4 + d) / c;
    }
}
