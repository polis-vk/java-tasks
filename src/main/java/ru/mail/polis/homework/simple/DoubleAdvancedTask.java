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
    //Вычисляем при помощи метода Виета-Кардано
    public static String equation(int a, int b, int c, int d) {
        if (a == 0) {
            if (b == 0) {
                if (c == 0) {
                    throw (new ArithmeticException("There is no equation"));
                }
                double x = ((double) d) / c;
                return Double.toString(x);
            }
            double D = Math.sqrt(Math.pow(c, 2) - 4 * b * d);
            double x1 = (-c - D) / 2 * b;
            double x2 = (-c + D) / 2 * b;
            return x2 + ", " + x1;
        } else if (b == 0 && c == 0) {
            return Math.cbrt(-d) + ", " + Math.cbrt(-d) + ", " + Math.cbrt(-d);
        }
        double aDer = (double) b / a;
        double bDer = (double) c / a;
        double cDer = (double) d / a;

        double q = (Math.pow(aDer, 2) - 3 * bDer) / 9;
        double r = (2 * Math.pow(aDer, 3) - 9 * aDer * bDer + 27 * cDer) / 54;

        double f = Math.acos(r / Math.sqrt(Math.pow(q, 3))) / 3;

        double x1 = -2 * Math.sqrt(q) * Math.cos(f) - aDer / 3;
        double x2 = -2 * Math.sqrt(q) * Math.cos(f + (2 * Math.PI / 3)) - aDer / 3;
        double x3 = -2 * Math.sqrt(q) * Math.cos(f - (2 * Math.PI / 3)) - aDer / 3;
        double maxX = Math.max(x1, Math.max(x2, x3));
        double minX = Math.min(Math.min(x1, x2), x3);
        return maxX + ", " + ((x1 + x2 + x3) - maxX - minX) + ", " + minX;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (Math.abs(a1 - a2) > 0.001) {
            return 0;
        } else if (Math.abs(a1) < 0.001) {
            return (float) Math.abs(b1 - b2);
        }
        double aInvert = -(1 / a1); //Вычисляем a функции, перпендикулярной a1
        double xCollision = (b1 - b2) / (a2 - aInvert); //Точка соприкосновения: aInvert * x + b1 = a2 * x + b2
        double yCollision = aInvert * xCollision + b1;
        // Точка начала отсчета расстояния будет (0, b1)
        return (float) Math.sqrt(Math.pow(xCollision, 2) + Math.pow(yCollision - b1, 2));
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
        //зададим первый и второй вектора
        int v1x = x2 - x1, v1y = y2 - y1, v1z = z2 - z1;
        int v2x = x3 - x1, v2y = y3 - y1, v2z = z3 - z1;
        //вычислим аргументы векторного произведения
        int i = v1y * v2z - v1z * v2y;
        int j = v1z * v2x - v1x * v2z;
        int k = v1x * v2y - v1y * v2x;
        //Получаем желанную точку, подставляя результаты в уравнение плоскости вида
        //i * (x - x1) + j * (y - y1) + k * (z - z1) = 0
        //->z4 = (k * z1 - (i * (x4 - x1) + j * (y4 - y1)))  / k
        return (k * z1 - (i * (x4 - x1) + j * (y4 - y1))) / (double) k;
    }
}
