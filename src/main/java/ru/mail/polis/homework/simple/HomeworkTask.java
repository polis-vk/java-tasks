package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        int steps = (int) ((b - a) / delta);
        double area = 0;
        for (int i = 0; i < steps; i++) {
            double value = 0.5 * (function.applyAsDouble(a + i * delta) + function.applyAsDouble(a + (i + 1) * delta));
            area += delta * value;
        }
        return area;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte c = 1;
        long max = -1;
        byte indexOfMax = 0;
        while (a != 0) {
            long number = a % 10;
            if (number >= max) {
                max = number;
                indexOfMax = c;
            }
            c++;
            a /= 10;
        }
        return (byte) (c - indexOfMax);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double a = (x3 - x1) * (y2 - y1);
        double b = x2 - x1;
        return a / b + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        int a = x1 * y2 - x2 * y1;
        int b = x2 * y3 - x3 * y2;
        int c = x3 * y4 - x4 * y3;
        int d = x4 * y1 - x1 * y4;
        return Math.abs((double) (a + b + c + d) / 2);
    }

}
