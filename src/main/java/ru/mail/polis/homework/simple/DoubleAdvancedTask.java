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
    // Using trigonometric Vieta's formula
    public static String equation(int a, int b, int c, int d) {
        assert a != 0;
        double bTilda = (double) b / a;
        double cTilda = (double) c / a;
        double dTilda = (double) d / a;

        double q = (bTilda * bTilda - 3 * cTilda) / 9;
        double r = (2 * Math.pow(bTilda, 3) - 9 * bTilda * cTilda + 27 * dTilda) / 54;
        double fi = 0;
        if (q != 0) {
            fi = Math.acos(r / Math.pow(q, (double) 3 / 2)) / 3;
        }

        double x1 = -2 * Math.pow(q, (double) 1 / 2) * Math.cos(fi + 2 * Math.PI / 3) - bTilda / 3;
        double x2 = -2 * Math.pow(q, (double) 1 / 2) * Math.cos(fi - 2 * Math.PI / 3) - bTilda / 3;
        double x3 = -2 * Math.pow(q, (double) 1 / 2) * Math.cos(fi) - bTilda / 3;
        return x1 + ", " + x2 + ", " + x3;
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
        if (a1 == 0) {
            return (float) Math.abs(b1 - b2);
        }
        // take a point on first line (0, b1)
        // perpendicular to these lines throw this point will be yPer = -1/a1 x + b1
        // yPer intersects second line at ((b1 - b2) / (a1 + 1/a1), (b1 - b2) / (a1 * a1 + 1 + b2))
        // the answer is the distance between these two points

        return (float) Math.sqrt(Math.pow(((b1 - b2) * a1) / (Math.pow(a1, 2) + 1), 2) +
                Math.pow(((b1 - b2) * Math.pow(a1, 2)) / (Math.pow(a1, 2) + 1) + b2 - b1, 2));
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
        // Ax + By + Cz + D = 0    surface equation
        double a = y1 * ((double) z2 - z3) + y2 * ((double) z3 - z1) + y3 * ((double) z1 - z2);
        double b = z1 * ((double) x2 - x3) + z2 * ((double) x3 - x1) + z3 * ((double) x1 - x2);
        double c = x1 * ((double) y2 - y3) + x2 * ((double) y3 - y1) + x3 * ((double) y1 - y2);
        double d = -(a * x1 + b * y1 + c * z1);

        return (-d - a * x4 - b * y4) / c;
    }
}
