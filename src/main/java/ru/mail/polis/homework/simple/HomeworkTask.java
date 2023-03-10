package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double result = 0.;
        for (double x = a; x < b; x += delta) {
            result += function.applyAsDouble(x) * delta;
        }
        return result;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte maxDigit = 0;
        byte someDigit;
        byte indexOfMaxDigit = 1;
        byte indexOfSomeDigit = 1;

        for (byte digitCount = (byte) (Math.log10(Math.abs(a)) + 1); digitCount > 0; --digitCount, ++indexOfSomeDigit) {
            someDigit = (byte) (a / Math.pow(10, digitCount - 1) % 10);
            if (maxDigit >= someDigit) {
                continue;
            }
            if (someDigit == 9) {
                return indexOfSomeDigit;
            }
            indexOfMaxDigit = indexOfSomeDigit;
            maxDigit = someDigit;
        }

        return indexOfMaxDigit;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (((double) y2 - y1) / (x2 - x1)) * (x3 - x1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double squareOfFirstTriangle = Math.abs((x2 - x1) * (y4 - y1) - (x4 - x1) * (y2 - y1)) / 2.;
        double squareOfSecondTriangle = Math.abs((x2 - x3) * (y4 - y3) - (x4 - x3) * (y2 - y3)) / 2.;
        return squareOfFirstTriangle + squareOfSecondTriangle;
    }

}
