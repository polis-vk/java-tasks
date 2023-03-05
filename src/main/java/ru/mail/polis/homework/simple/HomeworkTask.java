package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integral = 0;
        while (a <= b) {
            integral += function.applyAsDouble(a) * delta;
            a += delta;
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte maxDigitIndex = 0;
        long maxDigit = 0;
        byte numberLength = 0;
        while (a > 0) {
            if (maxDigit < Math.abs(a % 10)) {
                maxDigit = Math.abs(a % 10);
                maxDigitIndex = numberLength;
            }
            numberLength += 1;
            a /= 10;
        }
        return (byte) (numberLength - maxDigitIndex);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        if (y2 == y1) {
            return y2;
        }
        double vectorX12 = x2 - x1;
        double vectorX13 = x3 - x1;
        double factor = vectorX13 / vectorX12;
        double vectorY21 = y2 - y1;
        return vectorY21 * factor + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double sumX = x1 * y2 + x2 * y3 + x3 * y4 + x4 * y1;
        double sumY = y1 * x2 + y2 * x3 + y3 * x4 + y4 * x1;
        return Math.abs(sumX - sumY) / 2;
    }
}
