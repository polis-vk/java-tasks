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
        double root1;
        double root2;
        double root3;

        // Решение взято с сайта: https://math.fandom.com/ru/wiki/%D0%A2%D1%80%D0%B8%D0%B3%D0%BE%D0%BD%D0%BE%D0%BC%D0%B5%D1%82%D1%80%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B0%D1%8F_%D1%84%D0%BE%D1%80%D0%BC%D1%83%D0%BB%D0%B0_%D0%92%D0%B8%D0%B5%D1%82%D0%B0
        // делим исходное уравнение на a и получем уравнение вида x ^ 3 + a * x ^ 2 + b * x + c = 0
        double coefA = b * 1. / a;
        double coefB = c * 1. / a;
        double coefC = d * 1. / a;

        double q = (Math.pow(coefA, 2) - 3 * coefB) / 9;
        double r = (2 * Math.pow(coefA, 3) - 9 * coefA * coefB + 27 * coefC) / 54;
        double s = Math.pow(q, 3) - Math.pow(r, 2);

        if (Double.compare(s, 0.0) != 0) {
            double t = Math.acos(r / Math.sqrt(Math.pow(q, 3))) / 3;
            root1 = -2 * Math.sqrt(q) * Math.cos(t) - coefA / 3;
            root2 = -2 * Math.sqrt(q) * Math.cos(t + (2 * Math.PI / 3)) - coefA / 3;
            root3 = -2 * Math.sqrt(q) * Math.cos(t - (2 * Math.PI / 3)) - coefA / 3;
        } else {
            root1 = Math.cbrt(r) - coefA / 3;
            root2 = root1;
            root3 = -2 * Math.cbrt(r) - coefA / 3;
        }

        double x1;
        double x2;
        double x3;

        if (Double.compare(root1, root2) > 0 && Double.compare(root1, root3) > 0) {
            x1 = root1;
        } else if (Double.compare(root1, root2) < 0 && Double.compare(root2, root3) > 0) {
            x1 = root2;
        } else {
            x1 = root3;
        }

        if (Double.compare(root1, root2) < 0 && Double.compare(root1, root3) < 0) {
            x3 = root1;
        } else if (Double.compare(root1, root2) > 0 && Double.compare(root2, root3) < 0) {
            x3 = root2;
        } else {
            x3 = root3;
        }

        if (Double.compare(root1, x1) != 0 && Double.compare(root1, x3) != 0) {
            x2 = root1;
        } else if (Double.compare(root2, x1) != 0 && Double.compare(root2, x3) != 0) {
            x2 = root2;
        } else {
            x2 = root3;
        }

        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return (Double.compare(a1, a2) != 0) ? 0 : (float) (Math.abs(b2 - b1) / Math.sqrt(1 + Math.pow(a1, 2)));
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
        int y21 = y2 - y1;
        int z21 = z2 - z1;
        int y41 = y4 - y1;
        int x21 = x2 - x1;
        int x31 = x3 - x1;
        int y31 = y3 - y1;
        int z31 = z3 - z1;
        int x41 = x4 - x1;
        return ((double) (-x41 * (y21 * z31 - z21 * y31) + y41 * (x21 * z31 - z21 * x31)) / (x21 * y31 - y21 * x31)) + z1;
    }
}
