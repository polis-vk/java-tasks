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
        double p = (double) (3 * a * c - b * b) / (3 * a * a);
        double q = (double) (2 * b * b * b - 9 * a * b * c + 27 * a * a * d) / (27 * a * a * a);
        double kardanoD = q * q / 4 + p * p * p / 27;
        double sqrt = Math.sqrt(kardanoD);
        double yKardano1 = Math.pow(-q / 2 + sqrt, (double)1 / 3);
        double yKardano2 = Math.pow(-q / 2 - sqrt, (double)1 / 3);
        double angle;
        double atan = Math.atan(Math.sqrt(-kardanoD) / (-q / 2));
        if (q < 0) {
            angle = atan + Math.PI;
        } else if (q > 0) {
            angle = atan;
        } else angle = Math.PI / 2;
        double y1 = 2 * Math.sqrt(yKardano1 * yKardano2) * Math.cos(angle / 3);
        double y2 = 2 * Math.sqrt(yKardano1 * yKardano2) * Math.cos(2 * Math.PI / 3 + angle / 3);
        double y3 = 2 * Math.sqrt(yKardano1 * yKardano2) * Math.cos(4 * Math.PI / 3 + angle / 3);
        double x1 = y1 - (double) b / (3 * a);
        double x2 = y2 - (double) b / (3 * a);
        double x3 = y3 - (double) b / (3 * a);
        return x1 + ", " + x2 + ", " + x3;
    }

    public static void main(String[] args) {
        System.out.println(equation(1, -4, -7, 10));
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return (a1 == a2) ? (float) (Math.abs(b2 - b1) / Math.sqrt(1 + Math.pow(a1, 2))) : 0;
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
