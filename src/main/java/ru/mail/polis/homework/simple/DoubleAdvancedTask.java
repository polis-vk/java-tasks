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

    /**
     * Тригонометрическая формула Виета
     * https://www.wikiwand.com/ru/%D0%A2%D1%80%D0%B8%D0%B3%D0%BE%D0%BD%D0%BE%D0%BC%D0%B5%D1%82%D1%80%D0%B8%D1%87%D0%B5%D1%81%D0%BA%D0%B0%D1%8F_%D1%84%D0%BE%D1%80%D0%BC%D1%83%D0%BB%D0%B0_%D0%92%D0%B8%D0%B5%D1%82%D0%B0
     */
    public static String equation(int a, int b, int c, int d) {
        double root1;
        double root2;
        double root3;
        double at = (double) b / a;
        double bt = (double) c / a;
        double ct = (double) d / a;
        double Q = (at * at - 3 * bt) / 9;
        double R = (2 * at * at * at - 9 * at * bt + 27 * ct) / 54;
        double S = Q * Q * Q - R * R;
        if (S > 0) {
            double phi = Math.acos(R / Math.pow(Q, 3d / 2)) / 3;
            root1 = -2 * Math.sqrt(Q) * Math.cos(phi) - at / 3;
            root2 = -2 * Math.sqrt(Q) * Math.cos(phi + 2 * Math.PI / 3) - at / 3;
            root3 = -2 * Math.sqrt(Q) * Math.cos(phi - 2 * Math.PI / 3) - at / 3;
        } else {
            root1 = -2 * Math.cbrt(R) - at / 3;
            root2 = Math.cbrt(R) - at / 3;
            root3 = root2;
        }
        double x1 = Math.max(root1, Math.max(root2, root3));
        double x3 = Math.min(root1, Math.min(root2, root3));
        double x2 = root1 + root2 + root3 - x1 - x3;
        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return Math.abs(a2 - a1) < EPS ? (float) (Math.abs(b2 - b1) / Math.sqrt(a1 * a1 + 1)) : 0;
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
        int vectorX1 = x2 - x1;
        int vectorX2 = x3 - x1;
        int vectorY1 = y2 - y1;
        int vectorY2 = y3 - y1;
        int vectorZ1 = z2 - z1;
        int vectorZ2 = z3 - z1;
        int i = vectorY1 * vectorZ2 - vectorY2 * vectorZ1;
        int j = vectorX2 * vectorZ1 - vectorX1 * vectorZ2;
        int k = vectorX1 * vectorY2 - vectorX2 * vectorY1;
        return (k * z1 - (i * (x4 - x1) + j * (y4 - y1))) / (double) k;
    }
}
