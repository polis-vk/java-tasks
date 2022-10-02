package ru.mail.polis.homework.simple;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {

    static final double EPS = 1.0E-5;

    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */
    public static String equation(int a, int b, int c, int d) {
        double x1;
        double x2;
        double x3;
        // Метод Виета-Кардано
        double a1 = (double) b / a;
        double b1 = (double) c / a;
        double c1 = (double) d / a;
        double q = (Math.pow(a1, 2) - 3 * b1) / 9;
        double r = (2 * Math.pow(a1, 3) - 9 * a1 * b1 + 27 * c1) / 54;

        if (Math.pow(r, 2) < Math.pow(q, 3)) {
            double phi = Math.acos(r / Math.sqrt(Math.pow(q, 3))) / 3;
            x1 = -2 * Math.sqrt(q) * Math.cos(phi) - a1 / 3;
            x2 = -2 * Math.sqrt(q) * Math.cos(phi + (2 * Math.PI / 3)) - a1 / 3;
            x3 = -2 * Math.sqrt(q) * Math.cos(phi - (2 * Math.PI / 3)) - a1 / 3;
        } else {
            x1 = -2 * Math.cbrt(r) - a1 / 3;
            x2 = Math.cbrt(r) - a1 / 3;
            x3 = x2;
        }
        // Сортировка
        double max = Math.max(x1, Math.max(x2, x3));
        double min = Math.min(x1, Math.min(x2, x3));
        double mid = x1 + x2 + x3 - max - min;
        return max + ", " + mid + ", " + min;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return floatingEquals(a1, a2) ? (float) (Math.abs(b2 - b1) / Math.sqrt(1 + a1 * a2)) : 0;
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
        // Найдём координаты двух векторов на плоскости
        int a1 = x2 - x1;
        int b1 = y2 - y1;
        int c1 = z2 - z1;
        int a2 = x3 - x1;
        int b2 = y3 - y1;
        int c2 = z3 - z1;
        // Найдём коэффициенты для уравнения плоскости
        int a = b1 * c2 - b2 * c1;
        int b = a2 * c1 - a1 * c2;
        int c = a1 * b2 - b1 * a2;
        int d = (-a * x1 - b * y1 - c * z1);
        // Найдём z-координату 4 точки, подставив x и y в уравнение плоскости
        return (double) -(a * x4 + b * y4 + d) / c;
    }

    private static boolean floatingEquals(double a, double b) {
        return Math.abs(a - b) < EPS;
    }
}
