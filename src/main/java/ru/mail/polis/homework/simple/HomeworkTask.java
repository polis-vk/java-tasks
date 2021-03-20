package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double res = 0;

        for (double x = a; x <= b; x += delta) {
            res += delta * function.applyAsDouble(x);
        }

        return res;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        int indexOfMaxNumeralFromRightToLeft = 0;
        byte maxNumeral = 0;
        int  indexOfNowNumeralFromRightToLeft = 0;
        byte nowNumeral;

        while (a > 0) {
            indexOfNowNumeralFromRightToLeft++;
            nowNumeral = (byte) (a % 10);
            if (nowNumeral >= maxNumeral) {
                maxNumeral = nowNumeral;
                indexOfMaxNumeralFromRightToLeft = indexOfNowNumeralFromRightToLeft;
            }
            a /= 10;
        }

        return (byte) (indexOfNowNumeralFromRightToLeft - indexOfMaxNumeralFromRightToLeft + 1);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double equationForX3 = (double) (x3 - x1) / (x2 - x1);

        return equationForX3 * (y2 - y1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double firstTerm = x1 * y2 - y1 * x2;
        double secondTerm = x2 * y3 - y2 * x3;
        double thirdTerm = x3 * y4 - y3 * x4;
        double forthTerm = x4 * y1 - y4 * x1;

        return Math.abs(firstTerm + secondTerm + thirdTerm + forthTerm) / 2;
    }

}
