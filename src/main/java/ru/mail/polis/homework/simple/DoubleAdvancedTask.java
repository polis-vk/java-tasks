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
        if (b == 0 && c == 0 && d == 0) {
            return "0, 0, 0";
        }
        if (b == 0 && c == 0) {
            double root = Math.pow(((double) d) / ((double) a), 1.0 / 3.0);
            return root + ", " + root + ", " + root;
        }
        if (d == 0) {
            double[] roots = new double[3];
            int discriminant = b * b - 4 * a * c;
            if (discriminant > 0) {
                roots[0] = (-b + Math.sqrt(discriminant)) / (2 * a);
                roots[1] = (-b - Math.sqrt(discriminant)) / (2 * a);
            }
            if (discriminant == 0) {
                roots[0] = ((double) -b) / (2 * a);
            }
            sortForThree(roots);
            return roots[0] + ", " + roots[1] + ", " + roots[2];
        }
        return cardano(a, b, c, d);
    }

    // Формула Кардано
    // https://ru.intemodino.com/math/algebra/equations/cardano's-formula-for-solving-cubic-equations.html
    private static String cardano(double a, double b, double c, double d) {
        double[] roots = new double[3];
        double p = (3 * a * c - b * b) / (3 * a * a);
        double q = (2 * b * b * b - 9 * a * b * c + 27 * a * a * d) / (27 * a * a * a);
        double discriminant = q * q / 4 + p * p * p / 27;
        double toY = b / (3 * a);
        boolean complex = false;
        if (Math.abs(discriminant) < EPS) {
            discriminant = 0;
        }
        // discriminant > 0 => один действительный и два комплексных сопряженных, т. е.
        // x2 == a + bi; x3 == a - bi;
        if (discriminant > 0) {
            complex = true;
            double q1 = Math.pow(-q / 2 + Math.sqrt(discriminant), 1. / 3);
            double q2 = Math.pow(-q / 2 - Math.sqrt(discriminant), 1. / 3);
            roots[0] = q1 + q2 - toY;
            roots[1] = q1 + q2; // вещественная часть
            roots[2] = (q1 - q2) * Math.sqrt(3) / 2; // мнимая часть
        } else if (discriminant == 0) { // discriminant == 0
            if (Math.abs(q) < EPS) { // один действительный корень при q == 0
                roots[0] = -toY;
                roots[1] = roots[0];
                roots[2] = roots[0];
            } else { // два действительных корня при q != 0
                double dq = Math.pow(-q / 2, 1. / 3);
                roots[0] = 2 * dq - toY;
                roots[1] = -dq - toY;
                roots[2] = roots[1];
            }
        } else { // discriminant > 0 => 3 действительных корня
            double fi;
            if (Math.abs(q) < EPS) {
                fi = Math.PI / 2;
            } else {
                fi = Math.atan(Math.sqrt(-discriminant) / (-q / 2));
                if (q > 0) {
                    fi += Math.PI;
                }
            }
            double first = 2 * Math.sqrt(-p / 3);
            roots[0] = first * Math.cos(fi / 3) - toY;
            roots[1] = first * Math.cos((fi + 2 * Math.PI) / 3) - toY;
            roots[2] = first * Math.cos((fi + 4 * Math.PI) / 3) - toY;
        }
        if (!complex) {
            sortForThree(roots);
            return roots[0] + ", " + roots[1] + ", " + roots[2];
        } else {
            return roots[0] + ", " + roots[1] + "+" + roots[2] + "i, " + roots[1] + "-" + roots[2] + "i";
        }
    }

    private static void sortForThree(double[] a) {
        if (a[1] > a[0]) {
            double temp = a[0];
            a[0] = a[1];
            a[1] = temp;
        }
        if (a[1] > a[0]) {
            double temp = a[0];
            a[0] = a[1];
            a[1] = temp;
        }
        if (a[2] > a[1]) {
            double temp = a[1];
            a[1] = a[2];
            a[2] = temp;
        }
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return (a2 - a1 > EPS) ? 0 : (float) (Math.abs(b2 - b1) / Math.sqrt(1 + Math.pow(a1, 2)));
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
        int dx1 = x2 - x1;
        int dy1 = y2 - y1;
        int dz1 = z2 - z1;
        int dx2 = x3 - x1;
        int dy2 = y3 - y1;
        int dz2 = z3 - z1;
        // Коэффициенты для уравнения плоскости
        int a = dy1 * dz2 - dy2 * dz1;
        int b = dx2 * dz1 - dx1 * dz2;
        int c = dx1 * dy2 - dy1 * dx2;
        int d = -a * x1 - b * y1 - c * z1;
        return (double) -(a * x4 + b * y4 + d) / c;
    }
}
