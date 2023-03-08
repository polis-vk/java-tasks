package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double result = 0.0;
        double step = a;
        while (step <= b) {
            result += function.applyAsDouble(step) * delta;
            step += delta;
        }
        return result;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long number = a;
        byte result = 0;
        byte currentDigit = (byte) (number % 10);
        byte maxDigit = currentDigit;
        byte currentDigitIndex = 1;

        number /= 10;
        while (number > 0) {
            currentDigit = (byte) (number % 10);
            if (currentDigit >= maxDigit) {
                maxDigit = currentDigit;
                result = currentDigitIndex;
            }
            number /= 10;
            currentDigitIndex++;
        }
        return (byte) (currentDigitIndex - result);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return ((double) (x3 - x1) * (y2 - y1) / (x2 - x1) + y1);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double triangleSquare1 = 0.5 * Math.abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1));
        double triangleSquare2 = 0.5 * Math.abs((x3 - x1) * (y4 - y1) - (x4 - x1) * (y3 - y1));
        return triangleSquare1 + triangleSquare2;
    }

}
