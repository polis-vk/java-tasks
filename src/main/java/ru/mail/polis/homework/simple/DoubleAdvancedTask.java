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
        final double step = 0.00001;

        double prevX = step;
        double x = prevX + step;

        double x1 = 0;
        double x2 = 0;
        double x3 = 0;
        // Поиск первого корня
        if (d != 0)
            while (true) {
                if (
                    cubicFunctionValue(a, b, c, d, prevX) * cubicFunctionValue(a, b, c, d, x) <= 0
                    || cubicFunctionValue(a, b, c, d, -prevX) * cubicFunctionValue(a, b, c, d, -x) <= 0
                ) {
                    if (cubicFunctionValue(a, b, c, d, prevX) * cubicFunctionValue(a, b, c, d, x) <= 0) {
                        x1 = findRootForInterval(a, b, c, d, prevX, x);
                        if (cubicFunctionValue(a, b, c, d, -prevX) * cubicFunctionValue(a, b, c, d, -x) <= 0) {
                            x2 = findRootForInterval(a, b, c, d, -x, -prevX);
                        }
                    } else {
                        x1 = findRootForInterval(a, b, c, d, -x, -prevX);
                    }
                    break;
                }
                prevX = x;
                x += step;
            }
        if (x2 == 0 && (d != 0 || c != 0))
            while (true) {
                prevX = x;
                x += step;
                if (
                    cubicFunctionValue(a, b, c, d, prevX) * cubicFunctionValue(a, b, c, d, x) <= 0
                    || cubicFunctionValue(a, b, c, d, -prevX) * cubicFunctionValue(a, b, c, d, -x) <= 0
                ) {
                    if (cubicFunctionValue(a, b, c, d, prevX) * cubicFunctionValue(a, b, c, d, x) <= 0) {
                        x2 = findRootForInterval(a, b, c, d, prevX, x);
                        if (cubicFunctionValue(a, b, c, d, -prevX) * cubicFunctionValue(a, b, c, d, -x) <= 0) {
                            x3 = findRootForInterval(a, b, c, d, -x, -prevX);
                        }
                    } else {
                        x2 = findRootForInterval(a, b, c, d, -x, -prevX);
                    }
                    break;
                }
            }
        // Поиск третьего корня
        if (x3 == 0 && (b != 0 || c != 0 || d != 0))
            while (true) {
                prevX = x;
                x += step;
                if (
                    cubicFunctionValue(a, b, c, d, prevX) * cubicFunctionValue(a, b, c, d, x) <= 0
                    || cubicFunctionValue(a, b, c, d, -prevX) * cubicFunctionValue(a, b, c, d, -x) <= 0
                ) {
                    if (cubicFunctionValue(a, b, c, d, prevX) * cubicFunctionValue(a, b, c, d, x) <= 0) {
                        x3 = findRootForInterval(a, b, c, d, prevX, x);
                    }
                    else {
                        x3 = findRootForInterval(a, b, c, d, -x, -prevX);
                    }
                    break;
                }
            }
        // Сортировка найденных корней
        if (x3 > x2) {
            double temporary = x2;
            x2 = x3;
            x3 = temporary;
        }
        if (x2 > x1) {
            double temporary = x1;
            x1 = x2;
            x2 = temporary;
        }
        if (x3 > x2) {
            double temporary = x2;
            x2 = x3;
            x3 = temporary;
        }
        return x1 + ", " + x2 + ", " + x3;
    }

    public static double cubicFunctionDerivativeValue(int a, int b, int c, double x) {
        return 3 * a * x * x + 2 * b * x + c;
    }

    public static double cubicFunctionValue(int a, int b, int c, int d, double x) {
        return a * x * x * x + b * x * x + c * x + d;
    }

    public static double findRootForInterval(int a, int b, int c, int d, double begin, double end) {
        final double epsilon = 1e-10;
        double prevX = begin;
        double currentX = end;
        while (Math.abs(prevX - currentX) >= epsilon) {
            prevX = currentX;
            currentX = currentX
                    - cubicFunctionValue(a, b, c, d, currentX)
                    / cubicFunctionDerivativeValue(a, b, c, currentX);
        }
        return currentX;
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
        return (float) (Math.abs(b2 - b1) * Math.cos(Math.atan(a1)));
    }

    /**
     * Даны три точки в пространстве (x1, y1, z1) , (x2, y2, z2) и (x3, y3, z3). Вам нужно найти Z координату
     * четвертой точки (x4, y4, z4), которая находится на той же плоскости что и первые три.
     * (0, 0, 1,
     * 1, 1, 1,
     * 10, 100, 1,
     * 235, -5) -> 1
     */
    public static double surfaceFunction(int x1, int y1, int z1, int x2, int y2, int z2, int x3, int y3, int z3, int x4, int y4) {

        // Создадим и найдём координаты векторов u и v

        int uX = x2 - x1;
        int uY = y2 - y1;
        int uZ = z2 - z1;

        int vX = x3 - x1;
        int vY = y3 - y1;
        int vZ = z3 - z1;

        /*
         * Рассчитаем координаты вектора нормали к плоскости
         * с помощью формулы векторного произведения (через определитель)
         */

        int a = uY * vZ - uZ * vY;
        int b = vX * uZ - vZ * uX;
        int c = uX * vY - vX * uY;

        /*
         * Рассчитаем свободный член уравнения плоскости
         * (ax + by + cz + d = 0),
         * подставив в неё координаты первой точки в уравнение
         */

        int d = -a * x1 - b * y1 - c * z1;

        /*
         * Рассчитаем искомое значение z4 с помощью формулы
         * (ax + by + cz + d = 0),
         * подставив в неё координаты x4, y4 и найденные значения
         */
        return (double) (-d - a * x4 - b * y4) / c;
    }
}
