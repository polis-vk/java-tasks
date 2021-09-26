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
        // метод Виета-Кардано
        if (b == 0 && c == 0) {
            double x = Math.cbrt((double) -d / a);
            return x + ", " + x + ", " + x;
        }
        double reducedB = (double) b / a;
        double reducedC = (double) c / a;
        double reducedD = (double) d / a;
        double q = (reducedB * reducedB - 3 * reducedC) / 9;
        double r = (2 * reducedB * reducedB * reducedB - 9 * reducedB * reducedC + 27 * reducedD) / 54;
        double y = Math.acos(r / Math.sqrt(q * q * q)) / 3;
        double sqrtQ = Math.sqrt(q);
        double x1 = -2 * sqrtQ * Math.cos(y) - reducedB / 3;
        double x2 = -2 * sqrtQ * Math.cos(y + (2 * Math.PI / 3)) - reducedB / 3;
        double x3 = -2 * sqrtQ * Math.cos(y - (2 * Math.PI / 3)) - reducedB / 3;
        double minX = Math.min(Math.min(x1, x2), x3);
        double maxX = Math.max(Math.max(x1, x2), x3);
        return maxX + ", " + (x1 + x2 + x3 - maxX - minX) + ", " + minX;

    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (a1 != a2) {
            return 0;
        }
        return (float) (Math.abs(b2 - b1) / Math.sqrt(a1 * a1 + 1));
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
        int[] vector1 = new int[]{x2 - x1, y2 - y1, z2 - z1};
        int[] vector2 = new int[]{x3 - x1, y3 - y1, z3 - z1};

        int coefC = vector1[0] * vector2[1] - vector1[1] * vector2[0];
        if (coefC == 0) {
            return z1;
        }
        int coefA = vector1[1] * vector2[2] - vector1[2] * vector2[1];
        int coefB = vector1[0] * vector2[2] - vector1[2] * vector2[0];

        return (double) (coefA * (x1 - x4) - coefB * (y1 - y4) + coefC * z1) / coefC;
    }
}
