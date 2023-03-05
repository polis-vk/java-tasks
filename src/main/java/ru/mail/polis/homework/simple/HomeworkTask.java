package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {

        double integral = 0.0;

        for (double x = a; x <= b; x += delta){
            integral += function.applyAsDouble(x) * delta;
        }

        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {

        byte maxDigit = 0;
        byte reverseIndex = 0;
        byte i = 1;

        for  (; a != 0; i++, a /= 10){

            byte digit = (byte) (a % 10);
            if (digit >= maxDigit){
                maxDigit = digit;
                reverseIndex = i;
            }
        }

        int indexOfMaxDigit = i - reverseIndex;
        return (byte) indexOfMaxDigit;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {

        double absX2X1 = x2 - x1;
        double absY2Y1 = y2 - y1;
        double y3 = y2 + (x3 - x2) * (absY2Y1 / absX2X1);

        return y3;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {

        return Math.abs( (x1 - x2) * (y1 + y2) + (x2 - x3) * (y2 + y3) + (x3 - x4) * (y3 + y4) + (x4 - x1) * (y4 + y1) ) / 2.0;
    }

}
