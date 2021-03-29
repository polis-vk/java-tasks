package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integral = 0;
        double x = a; // текущее значение x

        while (x < b) {
            integral += function.applyAsDouble(x) * delta;
            x += delta;
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte max = 0;
        byte number = 0;
        byte numberLength = 0;
        long aCopy = a;
        while (aCopy > 0) {
            numberLength++;
            if ((aCopy % 10) >= max) {
                max = (byte) (aCopy % 10);
                number = numberLength;
            }
            aCopy = aCopy / 10;
        }
        if (numberLength == 0) {
            number = 1;
        }
        else {
            number = (byte) ((numberLength - number) + 1);
        }

        return number;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double y3;
        double k = (y2 - y1) / (double) (x2 - x1); // коэфициент наклона прямой
        double b = y1 - k * x1; // свободный член

        y3 = k * x3 + b;
        return y3;
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
