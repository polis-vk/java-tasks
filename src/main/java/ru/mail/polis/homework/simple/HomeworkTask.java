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

        for (double x = a; x <= b; x += delta) {
            integral += delta * function.applyAsDouble(x);
        }

        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        int size = 0;
        int pos = 0;
        int max = -1;

        while (a > 0) {
            int num = (int) (a % 10);
            size++;

            if (num >= max) {
                max = num;
                pos = size;
            }

            a = (a - a % 10) / 10;
        }

        return (byte) (size - pos + 1);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double k = (double) (y1 - y2) / (x1 - x2);
        double m = y1 - k * x1;
        return k * x3 + m;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double q = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        double w = Math.sqrt((x2 - x3) * (x2 - x3) + (y2 - y3) * (y2 - y3));
        double e = Math.sqrt((x3 - x4) * (x3 - x4) + (y3 - y4) * (y3 - y4));
        double r = Math.sqrt((x1 - x4) * (x1 - x4) + (y1 - y4) * (y1 - y4));
        double diagonal = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));

        double p1 = (q + w + diagonal) / 2;
        double p2 = (r + e + diagonal) / 2;

        double s1 = Math.sqrt(p1 * (p1 - q) * (p1 - w) * (p1 - diagonal));
        double s2 = Math.sqrt(p2 * (p2 - r) * (p2 - e) * (p2 - diagonal));

        return s1 + s2;
    }

}
