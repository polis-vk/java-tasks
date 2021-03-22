package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double resultCalc = 0;
        for (int i = 0; i < (b - a) / delta; i++) {
            resultCalc += function.applyAsDouble(delta * i + a);
        }
        return resultCalc * delta;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        if (a == 0) {
            return 1;
        }

        byte numberOfMax = 0;
        byte length = 0;
        byte maxDigit = 0;
        long numberA = a;

        while (numberA != 0) {
            byte digit = (byte) (numberA % 10);
            if (digit >= maxDigit) {
                maxDigit = digit;
                numberOfMax = length;
            }
            numberA /= 10;
            length++;
        }
        return (byte) (length - numberOfMax);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        if (x1 == x2 && y1 == y2) {
            System.out.println("Invalid coordinates");
            return 0;
        }

        return (double)((x3 - x1) * (y2 - y1)) / (x2 - x1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double side1 = DoubleTask.length(x1, y1, x2, y2);
        double side2 = DoubleTask.length(x2, y2, x3, y3);
        double side3 = DoubleTask.length(x3, y3, x4, y4);
        double side4 = DoubleTask.length(x4, y4, x1, y1);

        double diagonal = DoubleTask.length(x1, y1, x3, y3);

        double perimeter1 = (side1 + side2 + diagonal) / 2;
        double perimeter2 = (side3 + side4 + diagonal) / 2;

        double square1 = Math.sqrt(perimeter1 * (perimeter1 - side1) * (perimeter1 - side2) * (perimeter1 - diagonal));
        double square2 = Math.sqrt(perimeter2 * (perimeter2 - side3) * (perimeter2 - side4) * (perimeter2 - diagonal));

        return square1 + square2;
    }

}
