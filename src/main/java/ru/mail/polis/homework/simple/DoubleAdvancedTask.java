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
        // Используется тригонометрическая формула Виета (Википедия).
        double A = (double) b / a;
        double B = (double) c / a;
        double C = (double) d / a;
        double Q = (A * A - 3 * B) / 9;
        double R = (2 * A * A * A - 9 * A * B + 27 * C) / 54;
        double S = Q * Q * Q - R * R;
        double x1, x2, x3;
        if (S == 0.0) {
            x1 = (-2) * Math.signum(R) * Math.sqrt(Q) - (A / 3);
            x2 = Math.signum(R) * Math.sqrt(Q) - (A / 3);
            x3 = x2;
        } else {
            double f = (1.0 / 3.0) * Math.acos(R / Math.sqrt(Q * Q * Q));
            x1 = (-2) * Math.sqrt(Q) * Math.cos(f) - (A / 3);
            x2 = (-2) * Math.sqrt(Q) * Math.cos(f + ((2 * Math.PI) / 3.0)) - (A / 3);
            x3 = (-2) * Math.sqrt(Q) * Math.cos(f - ((2 * Math.PI) / 3.0)) - (A / 3);
        }
        double[] ans = new double[]{x1, x2, x3};
        // Сортировка вставками.
        for (int i = 1; i < 3; i++) {
            double x = ans[i];
            int j = i;
            while ((j > 0) && (ans[j - 1] < x)) {
                ans[j] = ans[j - 1];
                j--;
            }
            ans[j] = x;
        }
        return ans[0] + ", " + ans[1] + ", " + ans[2];
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return 0;
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
        return 0;
    }
}
