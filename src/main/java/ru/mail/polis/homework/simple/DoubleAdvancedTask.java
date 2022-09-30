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
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;
        if (b == 0 && c == 0 && d == 0) {
            return x1 + ", " + x2 + ", " + x3;
            // все три корня равны 0
        } else if (Math.abs(functionValue(a, b, c, d, -b / a)) < 0.0001 && Double.compare(b, 0) != 0 && Math.abs(functionValue(a, b, c, d, 0)) < 0.0001) {
            // если два корня нули, то другой корень равен -b/a
            x1 = -b / a;
            x2 = 0;
            x3 = 0;
        } else if (Math.abs(functionValue(a, b, c, d, 0)) < 0.001) {
            // если нашелся корень 0, то остальные 2 корня находятся по дискриминанту
            x1 = 0;
            x2 = (-b + Math.sqrt(b * b - 4 * a * c)) / (2 * a);
            x3 = c / (a * x2);
        } else {
            x1 = findingSolution(a, b, c, d);
            // находим 1 корень по методу секущих
            if ((a * x1 * x1 + b * x1) * (a * x1 * x1 + b * x1) + 4 * a * x1 * d < 0) {
                // Меньше нуля дискриминант может оказаться только из-за неточности метода секущих, так как в условии 3 корня
                x2 = (-(a * x1 * x1 + b * x1) + 0) / (2 * a * x1);
                // находим второй корень находим по формуле выведенной из теоремы Виетта
            } else {
                x2 = (-(a * x1 * x1 + b * x1) + Math.sqrt((a * x1 * x1 + b * x1) * (a * x1 * x1 + b * x1) + 4 * a * x1 * d)) / (2 * a * x1);
                // вставляем в формулу Виетта дискриминант
            }
            x3 = -d / (a * x1 * x2);
            // третий корень тоже находим по формуле выведенной из теоремы Виетта
        }
        double max1;
        double max2;
        double max3;
        max1 = Math.max(x1, Math.max(x2, x3));
        max3 = Math.min(x1, Math.min(x2, x3));
        max2 = x1 + x2 + x3 - max1 - max3;
        x1 = max1;
        x2 = max2;
        x3 = max3;
        return x1 + ", " + x2 + ", " + x3;
        // источник интернет, википедия
    }

    public static double functionValue(double a, double b, double c, double d, double x) {
        //расчет значения функции в данной точке
        return a * x * x * x + b * x * x + c * x + d;
    }

    public static double findingSolution(double a, double b, double c, double d) {
        //Метод секущих
        double x = 0;
        double xLast = Math.random() * 10, xGrandLast = Math.random() * 10;

        while (Math.abs(functionValue(a, b, c, d, xLast) - functionValue(a, b, c, d, xGrandLast)) < 0.01) {
            xLast = Math.random() * 10;
            xGrandLast = Math.random() * 10;
        }
        while (Math.abs(functionValue(a, b, c, d, x)) > 0.0000000000001) {
            x = xLast - functionValue(a, b, c, d, xLast) * (xLast - xGrandLast) / (functionValue(a, b, c, d, xLast) - functionValue(a, b, c, d, xGrandLast));
            xGrandLast = xLast;
            xLast = x;
        }
        return x;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        double result = 0;
        // записываем уравнения прямых в декартовой системе координат, как
        // ax + by + c1 = 0
        // ax + by + c2 = 0
        // в нашем случае b = 1, a1 и a2 это коэфициенты перед x, означающие наклон прямой,
        // с1 и с2 отвечают за точку пересечения с осью Y, по входящим параметрам b1 = c1, b2 = c2
        if (Double.compare(a1, a2) == 0) {
            // если a1 и a2 неравны, значит угол наклона разный, и прямые пересекутся, расстояние будет 0,
            // если же a1 и a2 равны, значит расстояние можно вычислить по формуле
            result = (Math.abs(b2 - b1)) / (Math.sqrt(a1 * a1 + 1));
        }
        // источник википедия
        return (float) result;
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
        // хотим составить уранение плоскости, ведь из него можно выразить z4,
        // записываем в матрицу все координаты, вместо z4 - z1 записываем просто z1,
        // одновременно считаем определитель методом треугольников и решаем уравнение выражая z4,
        // в итоге z4 это сумма коэфициентов которые должны были быть в уравнении плоскости поделить на определитель
        // источник - интернет
        int[][] matrix = {
                {x4 - x1, y4 - y1, -z1},
                {x2 - x1, y2 - y1, z2 - z1},
                {x3 - x1, y3 - y1, z3 - z1}
        };
        double z4 = (matrix[0][0] * matrix[1][1] * matrix[2][2] + matrix[2][1] * matrix[1][0] * matrix[0][2] + matrix[0][1] * matrix[1][2] * matrix[2][0]
                - matrix[2][0] * matrix[1][1] * matrix[0][2] - matrix[2][1] * matrix[1][2] * matrix[0][0] - matrix[1][0] * matrix[0][1] * matrix[2][2]);

        z4 = (-1) * z4 / (matrix[2][1] * matrix[1][0] - matrix[2][0] * matrix[1][1]);
        return z4;
    }
}
