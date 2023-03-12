package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

import java.lang.Math;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integralValue = 0;
        for (double x = a; x < b; x += delta) {
            integralValue += delta * function.applyAsDouble(x);
        }
        return integralValue;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte maxNumber = 0;
        byte currentNumber = 0;
        byte indexMaxNumber = 1;
        byte lengthNumber = (byte) Math.ceil(Math.log10(a + 1));
        byte maxPossibleNumber = 9;
        for (int i = 1; i <= lengthNumber; i++) {
            currentNumber = (byte) (a / Math.pow(10, lengthNumber - i) % 10);
            if (currentNumber > maxNumber) {
                maxNumber = currentNumber;
                indexMaxNumber = (byte) i;
            }
            if (maxNumber == maxPossibleNumber) {
                return indexMaxNumber;
            }
        }
        return indexMaxNumber;
    }

    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        //По формуле y3 = k * x3 + b
        double k = (double) (y1 - y2) / (x1 - x2);
        double b = y2 - k * x2;
        return k * x3 + b;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        //По формуле площади Гаусса
        return (double) (0.5 * Math.abs(x1 * y2 + x2 * y3 + x3 * y4 + x4 * y1 - x2 * y1 - x3 * y2 - x4 * y3 - x1 * y4));
    }
}
