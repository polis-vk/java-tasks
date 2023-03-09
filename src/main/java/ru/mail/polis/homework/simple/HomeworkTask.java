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
        for (double value = a; value < b; value += delta) {
            integralSum += function.applyAsDouble(value) * delta;
        }
        return integralSum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long number = a;
        int length = (int) (Math.abs(Math.log10(number)) + 1);
        byte maxDigit = 0;
        byte maxIndex = 1;
        for (byte currentIndex = 1; number != 0; currentIndex++) {
            byte currentDigit = (byte) (number / (Math.pow(10, length - currentIndex)));
            if (currentDigit > maxDigit) {
                if (currentDigit == 9) {
                    return currentIndex;
                }
                maxDigit = currentDigit;
                maxIndex = currentIndex;
            }
            number %= Math.pow(10, length - currentIndex);
        }
        return maxIndex;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return y1 + ((double) (y2 - y1) / (x2 - x1)) * (x3 - x1);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */

    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        //используется формула Брахмагупты
        double triangle1 = Math.abs(x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2)) / 2.0;
        double triangle2 = Math.abs(x1 * (y4 - y3) + x4 * (y3 - y1) + x3 * (y1 - y4)) / 2.0;
        return triangle1 + triangle2;
    }

}
