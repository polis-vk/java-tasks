package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integralSum = 0;
        for (double x = a; x < b; x += delta) {
            integralSum += delta * function.applyAsDouble(x);
        }
        return integralSum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte offset = -1;
        byte positionMaxValue = 1;
        byte lengthA = 0;
        int maxValue = 0;
        long comparator = 1;
        int currentNumber;
        while (comparator <= a) {
            lengthA++;
            currentNumber = (int) ((a / comparator) % 10);
            if (maxValue <= currentNumber) {
                maxValue = currentNumber;
                offset = lengthA;
            }
            positionMaxValue = (byte) (lengthA - offset + 1);
            comparator *= 10;
        }
        return positionMaxValue;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        if (x1 == x2) {
            throw new IllegalArgumentException("The entered coordinates x match. The value is not defined");
        }
        return y1 + (y2 - y1) * (x3 - x1) / (double) (x2 - x1);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return 0;
    }

}
