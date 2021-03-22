package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        if (delta <= 0) {
            System.out.println("HomeworkTask -> calcIntegral: Invalid argument \"delta\": " + delta + " <= 0");
            return -1.0;
        }

        double result = 0;
        for (int i = 0; i < (b - a) / delta; i++) {
            result += function.applyAsDouble(delta * i + a);
        }
        return result * delta;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        if (a == 0) {
            return 1;
        }

        byte numberOfMaxDigit = 0;
        long number = a;
        byte length = 0;
        byte max = 0;

        while (number != 0) {
            byte digit = (byte) (number % 10);
            if (digit >= max) {
                max = digit;
                numberOfMaxDigit = length;
            }
            number /= 10;
            length++;
        }
        return (byte) (length - numberOfMaxDigit);
    }

    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        if (x1 == x2 && y1 == y2) {
            System.out.println("HomeworkTask -> lineFunction(): Invalid coordinates");
            return 0;
        }

        return (double) ((x3 - x1) * (y2 - y1)) / (x2 - x1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        // Стороны четырёхугольника
        double side1 = DoubleTask.length(x1, y1, x2, y2);
        double side2 = DoubleTask.length(x2, y2, x3, y3);
        double side3 = DoubleTask.length(x3, y3, x4, y4);
        double side4 = DoubleTask.length(x4, y4, x1, y1);

        // Диоганаль четырёхугольника
        double d1 = DoubleTask.length(x1, y1, x3, y3);

        // Полупериметры треугольников
        double p1 = (side1 + side2 + d1) / 2;
        double p2 = (side3 + side4 + d1) / 2;

        // Вычисление площадей треугольников по формуле Герона
        double triangleSquare1 = Math.sqrt(p1 * (p1 - side1) * (p1 - side2) * (p1 - d1));
        double triangleSquare2 = Math.sqrt(p2 * (p2 - side3) * (p2 - side4) * (p2 - d1));

        return triangleSquare1 + triangleSquare2;
    }
}
