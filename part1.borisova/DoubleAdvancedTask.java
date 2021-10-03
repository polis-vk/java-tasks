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
        double[] roots = {x1, x2, x3};

        if (d != 0) {
            boolean change = false;
            int[] koeff = {a, b, c, d};
            if (a != 1) {
                change = true; //домножили на А^2, замена переменной y = a*x
                koeff[0] = 1;
                koeff[2] = a * c;
                koeff[3] = a * a * d;
            }
            int j = 0, k;
            for (int i = 1; i < Math.abs((double) koeff[3] / 2) + 1 && j < 1; i++) {
                if (koeff[3] % i == 0 && i * i * i * koeff[0] + i * i * koeff[1] + i * koeff[2] + koeff[3] == 0) {
                    roots[j] = i;
                    j++;
                }
                k = -i;
                if (koeff[3] % k == 0 && k * k * k * koeff[0] + k * k * koeff[1] + k * koeff[2] + koeff[3] == 0) {
                    roots[j] = k;
                    j++;
                }
            }
            if (change) {
                roots[0] /= a;
            }
        }

        //деление по схеме горнера
        double b1 = b + a * roots[0];
        double c1 = c + b1 * roots[0];

        //Решение квадратного уравнения
        double dis = b1 * b1 - 4 * a * c1;
        roots[1] = (-b1 - Math.sqrt(dis)) / (2 * a);
        roots[2] = (-b1 + Math.sqrt(dis)) / (2 * a);

        //сортировка 3х ответов
        if (roots[0] < roots[1]) {
            double temp = roots[0];
            roots[0] = roots[1];
            roots[1] = temp;
        }
        if (roots[1] < roots[2]) {
            double temp = roots[1];
            roots[1] = roots[2];
            roots[2] = temp;
        }
        if (roots[0] < roots[1]) {
            double temp = roots[0];
            roots[0] = roots[1];
            roots[1] = temp;
        }
        return roots[0] + ", " + roots[1] + ", " + roots[2];
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (a1 - a2 != 0) {
            return 0;
        }
        return (float) (Math.abs(b1 - b2) / Math.sqrt(a1 * a2 + 1));
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

        int[] v12 = {x2 - x1, y2 - y1, z2 - z1};
        int[] v13 = {x3 - x1, y3 - y1, z3 - z1};
        int[] n = {v12[1] * v13[2] - v12[2] * v13[1], -(v12[0] * v13[2] - v12[2] * v13[0]), v12[0] * v13[1] - v12[1] * v13[0]};
        int d = -(x1 * n[0] + y1 * n[1] + z1 * n[2]);
        double temp = -(x4 * n[0] + y4 * n[1] + d);
        return  temp / n[2];
    }
}
