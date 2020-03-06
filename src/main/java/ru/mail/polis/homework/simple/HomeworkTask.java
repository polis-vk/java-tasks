package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;


public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        // Вычисление интеграла с помощью составной квадратурной формулы Симпсона
        double integralPrev = delta + 1;
        double integral = 0;

        for (int N = 2; (N <= 4) || (Math.abs(integral - integralPrev) > delta); N += 2) {
            double h;
            double sum2 = 0;  //Значения с чётными индексами, которые нужно умножить на 2.
            double sum4 = 0;  //Значения с нечётными индексами, которые нужно умножить на 4.
            double sum = 0;
            h = (b - a) / (2 * N); //Шаг интегрирования.

            for (int i = 1; i <= 2 * N - 1; i += 2) {
                sum4 += function.applyAsDouble(a + h * i);
                sum2 += function.applyAsDouble(a + h * (i + 1));
            }
            sum = function.applyAsDouble(a) + 4 * sum4 + 2 * sum2 - function.applyAsDouble(b);
            integralPrev = integral;
            integral = (h / 3) * sum;
        }
        return integral;
    }


    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        // Без строк и массивов придумал только так...
        if (a > -10 && a < 10) {
            return 1;
        }
        long maxDigit = Long.MIN_VALUE;
        long b;
        long tmp = a;
        byte i = 0;
        byte res = 0;

        while (tmp > 0) {
            b = tmp % 10;
            tmp = tmp / 10;
            if (b >= maxDigit) {
                maxDigit = b;
                res = i;
            }
            i++;
        }
        return (byte) Math.abs(i - res);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double k = (double) (y2 - y1) / (x2 - x1);
        double b = y1 - k * x1;
        return k * x3 + b;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        // Разбиваю на два треугольника, для каждого считаю площадь по формуле Герона. Возвращаю их сумму.
        double a = DoubleTask.length(x1, y1, x2, y2);
        double b = DoubleTask.length(x2, y2, x3, y3);
        double c = DoubleTask.length(x3, y3, x4, y4);
        double d = DoubleTask.length(x4, y4, x1, y1);

        double d1 = DoubleTask.length(x1, y1, x3, y3); // диагональ
        return (geron(a, b, d1) + geron(c, d, d1));

    }

    // Ввёл дополнительную функцию.
    public static double geron(double a, double b, double c) {
        double p = (a + b + c) / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

}
