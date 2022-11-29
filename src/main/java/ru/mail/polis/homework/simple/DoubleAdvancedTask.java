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
    public static String equation(int a, int b, int c, int d) {
        //Способ: Тригонометрическая формула Виета
        //Решение: https://math.fandom.com/ru/wiki/Тригонометрическая_формула_Виета
        double[] roots = new double[3];

        double A = (double) b / a;
        double B = (double) c / a;
        double C = (double) d / a;

        double Q = (Math.pow(A, 2) - 3 * B) / 9;
        double R = (2 * Math.pow(A, 3) - 9 * A * B + 27 * C) / 54;
        double S = Math.pow(Q, 3) - Math.pow(R, 2);

        if (S > 0) {
            double f = Math.acos(R / Math.sqrt(Math.pow(Q, 3))) / 3;
            roots[0] = -2 * Math.sqrt(Q) * Math.cos(f) - A / 3;
            roots[1] = -2 * Math.sqrt(Q) * Math.cos(f + 2 * Math.PI / 3) - A / 3;
            roots[2] = -2 * Math.sqrt(Q) * Math.cos(f - 2 * Math.PI / 3) - A / 3;
        } else if (S < 0) {
            double f = getArch(Math.abs(R / Math.sqrt(Math.abs(Math.pow(Q, 3)))));
            double T = Math.signum(R) * Math.sqrt(Math.abs(Q)) * getCh(f);
            roots[0] = -2 * T - A / 3;
        } else {
            double T = Math.signum(R) * Math.sqrt(Math.abs(Q));
            roots[0] = -2 * T - A / 3;
            roots[1] = T - A / 3;
        }

        Arrays.sort(roots);
        return roots[2] + ", " + roots[1] + ", " + roots[0];
    }

    //Рассчитывает ареакосинус
    private static double getArch(double z) {
        return Math.log(z + Math.sqrt(Math.pow(z, 2) - 1));
    }

    //Рассчитывает гиперболический косинус
    private static double getCh(double z) {
        return (Math.pow(Math.E, z) + Math.pow(Math.E, -z)) / 2;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (Double.compare(a1, a2) != 0) {
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
        //Задача решается через условие принадлежности четырех точек одной плоскости
        //Координаты первого вектора
        double xv1 = x2 - x1;
        double yv1 = y2 - y1;
        double zv1 = z2 - z1;

        //Координаты второго вектора
        double xv2 = x3 - x1;
        double yv2 = y3 - y1;
        double zv2 = z3 - z1;

        //Координаты x и y третьего вектора
        double xv3 = x4 - x1;
        double yv3 = y4 - y1;

        return z1 + (-xv3 * yv1 * zv2 - xv2 * yv3 * zv1 +
                xv1 * yv3 * zv2 + xv3 * zv1 * yv2) / (xv1 * yv2 - yv1 * xv2);
    }
}
