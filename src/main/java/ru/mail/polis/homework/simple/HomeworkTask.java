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
        double x = a;
        while (x <= b) {
            integralSum += function.applyAsDouble(x) * delta;
            x += delta;
        }
        return integralSum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte index = 1;
        byte counter = 1;
        byte maxNumber = -1;
        while (a > 0) {
            if (a % 10 >= maxNumber) {
                maxNumber = (byte) (a % 10);
                index = counter;
            }
            a /= 10;
            counter++;
        }
        if (counter == 1) {
            return 1;
        }
        return (byte) (counter - index);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double k = (double) (y1 - y2) / (x1 - x2);
        double b = y2 - k * x2;
        double y3 = k * x3 + b;
        return y3;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return 0;
    }

}
