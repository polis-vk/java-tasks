package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double result = 0;
        for (double i = a; i <= b; i += delta) {
            result += function.applyAsDouble(i) * delta;
        }
        return result;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long aCopy = a;
        byte[] reverseNumbers = new byte[19];
        byte sizeOfArray = 0;
        do {
            byte remains;
            remains = (byte) Math.abs(aCopy % 10);
            reverseNumbers[sizeOfArray] = remains;
            aCopy /= 10;
            ++sizeOfArray;
        } while (aCopy != 0);

        byte maxDigitNumber = 0;
        byte maxDigit = reverseNumbers[0];
        for (byte j = 1; j < sizeOfArray; ++j) {
            if (reverseNumbers[j] >= maxDigit) {
                maxDigitNumber = j;
                maxDigit = reverseNumbers[j];
            }
        }

        return (byte) Math.abs(maxDigitNumber - sizeOfArray);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double k = (double) (y2 - y1) / (x2 - x1);
        double b = y1 - k * x1;
        return k * x3 + b;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return 0.5 * Math.abs(x1 * y2 + x2 * y3 + x3 * y4 + x4 * y1 - x2 * y1 - x3 * y2 - x4 * y3 - x1 * y4);
    }

}
