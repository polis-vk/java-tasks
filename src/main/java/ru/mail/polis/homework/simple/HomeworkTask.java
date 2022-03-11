package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double sum = 0;
        for (double interval = a; interval < b; interval += delta) {
            sum += function.applyAsDouble(interval) * delta;
        }
        return sum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte max = -1;
        byte digit;
        byte sequenceNumber = 0;    // from right to left
        byte numberOfDigits = 0;
        long temp = a;
        while (temp > 0) {
            digit = (byte) (temp % 10);
            numberOfDigits++;
            if (digit >= max) {
                max = digit;
                sequenceNumber = numberOfDigits;
            }
            temp /= 10;
        }
        return (byte) (numberOfDigits - sequenceNumber + 1);
    }

    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (double) -((x1 * y2 - x2 * y1) + (y1 - y2) * x3) / (x2 - x1);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return getTriangleSquare(x1, y1, x2, y2, x3, y3) + getTriangleSquare(x1, y1, x3, y3, x4, y4);
    }

    // Calculating the area of a triangle
    private static double getTriangleSquare(double x1, double y1, double x2, double y2, double x3, double y3) {
        return Math.abs(((x1 - x2) * (y3 - y2) - (y1 - y2) * (x3 - x2)) / 2);
    }

}
