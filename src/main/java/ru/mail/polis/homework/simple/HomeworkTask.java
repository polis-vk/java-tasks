package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

import static java.lang.Math.*;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integral = 0.0;
        double c = a;

        while (b > c) {
            integral += (function.applyAsDouble(c) * delta);
            c += (delta);

        }

        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long d = a;
        int count = 0, Max_ch = 0, index = 0;

        while (d > 0) {
            count++;
            if (Max_ch <= (d % 10)) {
                index = count;
                Max_ch = (int) d % 10;
            }
            d /= 10;
        }
        return (byte) (count - index + 1);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double y3;
        if (x1 != x2) {
            return y3 = (double) (((double) (y2 - y1) * (x3 - x1) / (double) (x2 - x1)) + y1);
        }
        System.out.println("Error");
        return 0;
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
