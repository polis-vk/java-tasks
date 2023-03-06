package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta)
    {
        double integral = 0;
        for (double d = a; d < b; d += delta) {
            integral += function.applyAsDouble(d) * delta;
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a)
    {
        byte maxDecimal = 0;
        byte maxNumber = 0;
        long num = a;
        byte i = 0;
        while (num != 0) {
            if (Math.abs(num) % 10 >= maxDecimal) {
                maxDecimal = (byte)(Math.abs(num) % 10);
                maxNumber = i;
            }
            num = Math.abs(num) / 10;
            i++;
        }
        return (byte)( i - maxNumber );
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3)
    {
        // y = kx + b
        double k, b, y3;
        k = (double)(y2 - y1) / (x2 - x1);
        b = y1 - k * x1;
        y3 = k * x3 + b;
        return y3;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4)
    {
        //площадь как сумма двух треугольников
        // S = 1/2 module((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1))
        double S1, S2;
        S1 = 0.5 * Math.abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1));
        S2 = 0.5 * Math.abs((x3 - x1) * (y4 - y1) - (x4 - x1) * (y3 - y1));
        return S1 + S2;
    }

}
