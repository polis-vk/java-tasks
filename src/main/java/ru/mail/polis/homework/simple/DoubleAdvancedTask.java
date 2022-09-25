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
        // Воспользуемся формулой Кардано
        double A = (double) b / a;
        double B = (double) c / a;
        double C = (double) d / a;
        double p = B - (A * A / 3.0);
        double q = 2.0 * A * A * A / 27.0 - A * B / 3.0 + C;
        double delta = q * q / 4.0 + p * p * p / 27.0;
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;
        if (delta > 0) {
            double t1 = -q / 2.0 + Math.sqrt(delta);
            double t2 = -q / 2.0 - Math.sqrt(delta);
            double m1 = t1 < 0 ? -Math.pow(-t1, 1.0 / 3.0) : Math.pow(t1, 1.0 / 3.0);
            double m2 = t2 < 0 ? -Math.pow(-t2, 1.0 / 3.0) : Math.pow(-t2, 1.0 / 3.0);
            x1 = m1 + m2 - A / 3.0;
        }
        else if (delta == 0){
            if (q < 0) {
                x1 = 2 * Math.pow(-q / 2.0, 1.0 / 3.0) - A / 3.0;
                x2 = -Math.pow(-q / 2.0,  1.0 / 3.0) - A / 3.0;

            } else {
                x1 = -2 * Math.pow(q / 2.0, 1.0 / 3.0) - A / 3.0;
                x2 = Math.pow(q / 2.0, 1.0 / 3.0) - A / 3.0;
            }
        }
        else {
            double t = (1.0 / 3.0) * Math.asin(((3 * Math.sqrt(3) * q) / (2 * Math.pow(Math.pow(-p, 1.0 / 2.0), 3.0))));
            x1 = (2.0 / Math.sqrt(3)) * (Math.sqrt(-p) * Math.sin(t)) - A / 3.0;
            x2 = (-2.0 / Math.sqrt(3)) * (Math.sqrt(-p) * Math.sin(t + (Math.PI / 3.0))) - A / 3.0;
            x3 = (2.0 / Math.sqrt(3)) * (Math.sqrt(-p) * Math.cos(t + (Math.PI / 6.0))) - A / 3.0;
        }
        return Math.max(x1, Math.max(x2, x3)) + ", " + (x1 + x2 + x3 - Math.max(x1, Math.max(x2, x3))
                - Math.min(x1, Math.min(x2, x3))) + ", " + Math.min(x1, Math.min(x2, x3));

    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return a1 != a2 ? 0 : (float) (Math.abs(b2 - b1) / Math.sqrt(1 + a1 * a2));
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

        // Сначала найдем уравнение плоскости по трем точка в виде: a * x + b * y + c * z + d = 0
        double a1 = x2 - x1;
        double b1 = y2 - y1;
        double c1 = z2 - z1;
        double a2 = x3 - x1;
        double b2 = y3 - y1;
        double c2 = z3 - z1;
        double a = b1 * c2 - b2 * c1;
        double b = a2 * c1 - a1 * c2;
        double c = a1 * b2 - b1 * a2;
        double d = -a * x1 - b * y1 - c * z1;
        // Теперь подставим x4 и y4 и выразим искомое z
        return (-a * x4 - b * y4 - d) / c;

    }
}
