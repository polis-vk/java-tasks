package ru.mail.polis.homework.simple;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

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

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat decimalFormat = new DecimalFormat("#.##############", symbols);

        if (a != 0 && b == 0 && c == 0) {
            x1 = Math.cbrt((double) -d / a);
        } else {
            //Решение кубического уравнения методом Виетта
            double aa = (double) b / a;
            double bb = (double) c / a;
            double cc = (double) d / a;

            double q = (aa * aa - 3 * bb) / 9;
            double r = (2 * aa * aa * aa - 9 * aa * bb + 27 * cc) / (54);
            double s = q * q * q - Math.pow(r, 2);

            if (s > 0) {
                double fi = (double) 1 / 3 * Math.acos(r / Math.sqrt(Math.pow(q, 3)));
                x1 = -2 * Math.sqrt(q) * Math.cos(fi) - aa / 3;
                x2 = -2 * Math.sqrt(q) * Math.cos(fi + (double) 2 / 3 * Math.PI) - aa / 3;
                x3 = -2 * Math.sqrt(q) * Math.cos(fi - (double) 2 / 3 * Math.PI) - aa / 3;
            } else if (s == 0) {
                x1 = -2 * Math.cbrt(r) - aa / 3;
                x2 = Math.cbrt(r) - aa / 3;
            }
        }

        double min = Math.min(Math.min(x1, x2), x3);
        double max = Math.max(Math.max(x1, x2), x3);
        x2 = (x1 + x2 + x3) - min - max;
        x1 = max;
        x3 = min;

        String solution = decimalFormat.format(x1) + ", " + decimalFormat.format(x2) + ", " + decimalFormat.format(x3);
        return solution;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        //Если прямые параллельны, находим расстояние между 1 прямой и точкой на 2 прямой
        if (a1 == a2) {
            float lenght = (float) (Math.abs(b2 - b1) / Math.sqrt(a1 * a1 + 1));
            return lenght;
        } else return 0;
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
        //Находим уравнение плоскости с помощью матрицы
        double a = (y2 - y1) * (z3 - z1) - (y3 - y1) * (z2 - z1);
        double b = -(x2 - x1) * (z3 - z1) + (x3 - x1) * (z2 - z1);
        double c = (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1);
        double d = -x1 * a - y1 * b - z1 * c;

        //Зная уравнение плоскости находим искомую координату
        double z4 = (-d - a * x4 - b * y4) / c;
        return z4;
    }
}
