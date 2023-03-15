package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double func = 0;
        for (double x = a; x < b; x += delta) {
            func += function.applyAsDouble(x) * delta;
        }
        return func;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        if (a == 0) {
            return 1;
        }
        byte position = 0;
        byte maxPosition = 1;
        byte maxDigit = 0;
        byte digit;
        long num = a;
        byte numOfDigits = (byte) Math.ceil(Math.log10(a));
        for (int i = 1; i <= numOfDigits; i++) {
            digit = (byte) (num / (Math.pow(10, numOfDigits - i)));
            position++;
            if (digit == 9) {
                return position;
            }
            if (digit > maxDigit) {
                maxDigit = digit;
                maxPosition = position;
            }
            num %= Math.pow(10, numOfDigits - i);
        }
        return maxPosition;
    }

    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (double) ((x3 - x1) * (y2 - y1)) / (x2 - x1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double d1 = DoubleTask.length(x1, y1, x3, y3);
        double d2 = DoubleTask.length(x2, y2, x4, y4);
        if (d1 == 0 || d2 == 0) {
            return 0;
        }
        double sin = Math.abs((x3 - x1) * (y4 - y2) - (y3 - y1) * (x4 - x2)) / (d1 * d2);
        return 0.5 * d1 * d2 * sin;
    }

}
