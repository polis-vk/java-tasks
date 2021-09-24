package ru.mail.polis.homework.simple;

import java.math.BigInteger;
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
        // Тригонометрическая формула Виета
        double x1, x2, x3;
        double at = (double) b / a;
        double bt = (double) c / a;
        double ct = (double) d / a;
        double q = (at * at - 3 * bt) / 9;
        double r = (2 * at * at * at - 9 * at * bt + 27 * ct) / 54;
        // Умножаем q на a^2, r на а^3 и изменяем формулу s на q^3*4 - r^2 чтобы s было целочисленным.
        BigInteger ab = BigInteger.valueOf(a);
        BigInteger bb = BigInteger.valueOf(b);
        BigInteger cb = BigInteger.valueOf(c);
        BigInteger db = BigInteger.valueOf(d);
        BigInteger qd = bb.pow(2).subtract(ab.multiply(cb).multiply(BigInteger.valueOf(3)));
        BigInteger rd = BigInteger.valueOf(2).multiply(bb.pow(3))
                .subtract(BigInteger.valueOf(9).multiply(bb).multiply(cb).multiply(ab))
                .add(BigInteger.valueOf(27).multiply(db).multiply(ab.pow(2)));
        BigInteger s = qd.pow(3).multiply(BigInteger.valueOf(4)).subtract(rd.pow(2));
        double[] x = new double[3];
        if (s.signum() == 1) {
            double fi = Math.acos(r / Math.pow(q, 1.5)) / 3;
            x[0] = -2 * Math.sqrt(q) * Math.cos(fi) - at / 3;
            x[1] = -2 * Math.sqrt(q) * Math.cos(fi + 2 * Math.PI / 3) - at / 3;
            x[2] = -2 * Math.sqrt(q) * Math.cos(fi - 2 * Math.PI / 3) - at / 3;
        } else if (s.signum() == -1) {
            double archArg = Math.abs(r) / Math.pow(Math.abs(q), 1.5);
            double fi = Math.log(archArg + Math.sqrt(archArg * archArg - 1));
            x[0] = -2 * Math.signum(r) * Math.sqrt(Math.abs(q)) * Math.cosh(fi) - at / 3;
            x[1] = x[0];
            x[2] = x[0];
        } else {
            x[0] = -2 * Math.cbrt(r) - at / 3;
            x[1] = Math.cbrt(r) - at / 3;
            x[2] = x[1];
        }
        Arrays.sort(x);
        x1 = x[2];
        x2 = x[1];
        x3 = x[0];
        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (a1 == a2) {
            return (float) (Math.abs(b2 - b1) / Math.sqrt(a1 * a1 + 1));
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
        int a, b, c, d;
        a = (y2 - y1) * (z3 - z1) - (y3 - y1) * (z2 - z1);
        b = (x3 - x1) * (z2 - z1) - (x2 - x1) * (z3 - z1);
        c = (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1);
        d = -a * x1 - b * y1 - c * z1;
        return (double) (a * x4 + b * y4 + d) / -c;
    }
}
