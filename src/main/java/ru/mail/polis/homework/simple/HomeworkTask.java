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
        for (double x = a; x <= b; x += delta) {
            integral += function.applyAsDouble(x) * delta;
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte max = 0;
        byte indexMax = 0;
        byte amountOfDigits = 1;
        while (a != 0) {
            byte digit = (byte) (a % 10);
            if (digit >= max) {
                max = digit;
                indexMax = amountOfDigits;
            }
            a /= 10;
            amountOfDigits++;
        }

        return (byte) (amountOfDigits - indexMax);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        float k = (float) (y2 - y1) / (x2 - x1);
        float b = y1 - k * x1;
        return k * x3 + b;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double ab = DoubleTask.length(x1, y1, x2, y2);
        double bc = DoubleTask.length(x2, y2, x3, y3);
        double cd = DoubleTask.length(x3, y3, x4, y4);
        double da = DoubleTask.length(x4, y4, x1, y1);

        double d = DoubleTask.length(x1, y1, x3, y3);

        double perimeter1 = (ab + bc + d) / 2;
        double perimeter2 = (cd + da + d) / 2;

        double square1 = Math.sqrt(perimeter1 * (perimeter1 - ab) * (perimeter1 - bc) * (perimeter1 - d));
        double square2 = Math.sqrt(perimeter2 * (perimeter2 - cd) * (perimeter2 - da) * (perimeter2 - d));

        return square1 + square2;

    }

}
