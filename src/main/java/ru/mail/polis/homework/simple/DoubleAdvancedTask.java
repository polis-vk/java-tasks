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
        double fx, x1, derfx;

        // первый корень находим методом Ньютона

        do {
            x1 = (Math.random() - 0.5);
            fx = getValueOfCubic(a, b, c, d, x1);
            derfx = getDerValueOfCubuc(a, b, c, x1);
        } while (derfx == 0); // метод Ньютона не будет работать, если производная равна нулю

        while (fx != 0) {
            x1 = x1 - fx / derfx;
            fx = getValueOfCubic(a, b, c, d, x1);
            derfx = getDerValueOfCubuc(a, b, c, x1);
        }

        x1 = Math.round(x1 * 1e12) / 1e12; // округление до 12 знаков после запятой - необходимо из-за погрешности,
        // из-за которой иногда дискриминант < 0 на следующем шаге

        double quadraticA = a;
        double quadraticB = b + a * x1;
        double quadraticC = c + x1 * quadraticB;

        // если мы поделим исходный полином на полином (x-x1), то получим полином с коэффициентами quadratic(A,B,C)
        // при условии, что x1 - корень исходного полинома

        double discriminant = Math.sqrt(Math.pow(quadraticB, 2) - 4 * quadraticA * quadraticC);

        double x2 = (-quadraticB + discriminant) / (2 * quadraticA);
        double x3 = (-quadraticB - discriminant) / (2 * quadraticA);

        double tmp;

        if (x1 < x2) {
            tmp = x1;
            x1 = x2;
            x2 = tmp;
        }

        if (x1 < x3) {
            tmp = x1;
            x1 = x3;
            x3 = tmp;
        }

        if (x2 < x3) {
            tmp = x2;
            x2 = x3;
            x3 = tmp;
        }

        return x1 + ", " + x2 + ", " + x3;
    }


    // значение производной полинома в точке x
    public static double getDerValueOfCubuc(int a, int b, int c, double x) {
        return 3 * a * Math.pow(x, 2) + 2 * b * x + c;
    }

    // значение полинома в точке x
    public static double getValueOfCubic(int a, int b, int c, int d, double x) {
        return a * Math.pow(x, 3) + b * Math.pow(x, 2) + c * x + d;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (a1 == a2) {
            return (float) (Math.abs(b1 - b2) / Math.sqrt(1 + a1 * a1));
        } else {
            return 0;
        }
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

        // введем три вектора, начало которых будет в точке 1, концы - в точках 2, 3, 4
        // тогда получим у них следующие координаты

        int rx1 = x2 - x1;
        int rx2 = x3 - x1;
        int rx3 = x4 - x1;
        int ry1 = y2 - y1;
        int ry2 = y3 - y1;
        int ry3 = y4 - y1;
        int rz1 = z2 - z1;
        int rz2 = z3 - z1;

        // тогда наши векторы компланарны, то есть определитель матрицы, в которой построчно записаны их координаты - 0
        // расписав равенство det(A) = 0, получим следущее равенство для rz3

        double rz3 = ((double) (rz1 * ry2 * rx3 + ry3 * rx1 * rz2 - rz1 * rx2 * ry3 - ry1 * rz2 * rx3)) / (rx1 * ry2 - rx2 * ry1);

        // при возврате ответа, сменим точку отсчета назад (векторы у нас считаются относительно точки 1)
        return rz3 + z1;
    }
}
