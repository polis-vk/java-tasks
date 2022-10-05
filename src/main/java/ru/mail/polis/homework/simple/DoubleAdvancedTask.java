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
        double a1 = (double) b / a;
        double b1 = (double) c / a;
        double c1 = (double) d / a;
        if (d == 0) { // Попытка свести задачу к квадратному уравнению
            double D = b * b - 4 * a * c;
            if (D >= 0) {
                x1 = (-b + Math.sqrt(D)) / (2 * a);
                x2 = (-b - Math.sqrt(D)) / (2 * a);
            }
        } else { // Решение c помощью тригонометрической формулы Виета
            double Q = (a1 * a1 - 3 * b1) / 9d;
            double R = (2 * a1 * a1 * a1 - 9 * a1 * b1 + 27 * c1) / 54d;
            double S = Q * Q * Q - R * R;
            if (S > 0) {
                double f = Math.acos(R / Math.pow(Q, 1.5d)) / 3d;
                x1 = (-2 * Math.sqrt(Q) * Math.cos(f)) - (a1 / 3d);
                x2 = (-2 * Math.sqrt(Q) * Math.cos(f + (2 * Math.PI / 3d))) - (a1 / 3d);
                x3 = (-2 * Math.sqrt(Q) * Math.cos(f - (2 * Math.PI / 3d))) - (a1 / 3d);
            } else if (S == 0) {
                x1 = -2 * Math.pow(R, 1.5d) - a1 / 3;
                x2 = Math.pow(R, 1.5d) - a1 / 3;
            } // При прочих S имеем мнимые корни
        }
        double maxX = Math.max(x1, Math.max(x2, x3));
        double minX = Math.min(x1, Math.min(x2, x3));
        return maxX + ", " + (x1 + x2 + x3 - minX - maxX) + ", " + minX;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        // y1 = a1*x1 + b1; y2 = a2*x2 + b2
        if (Math.abs(a1 - a2) <= 0.000001) { // Проверка на параллельность
            return (float) (Math.abs(b1 - b2) / Math.sqrt(a1 * a1 + 1));
        }
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
        // Получаем уравнение плоскости
        double a = (y2 - y1) * (z3 - z1) - (y3 - y1) * (z2 - z1);
        double b = (z2 - z1) * (x3 - x1) - (z3 - z1) * (x2 - x1);
        double c = (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1);
        double d = - x1 * a - y1 * b - z1 * c;
        return - (a * x4 + b * y4 + d) / c;
    }
}
