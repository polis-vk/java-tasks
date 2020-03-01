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
        for (double i = a; i <= b; i += delta) {
            integral += delta * function.applyAsDouble(i);
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte position = 0;
        byte max = 0;
        byte length = 1;
        while (a > 0) {
            if (a % 10 >= max) {
                max = (byte) (a % 10);
                position = length;
            }
            length++;
            a /= 10;
        }
        return (byte) (length - position);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double k = (y2 - y1) * 1.0 / (x2 - x1);
        return k * (x3 - x1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double d1 = DoubleTask.length(x1, y1, x3, y3);
        double a = DoubleTask.length(x1, y1, x2, y2);
        double b = DoubleTask.length(x2, y2, x3, y3);
        double p = (d1 + a + b) / 2;
        double s = Math.sqrt(p * (p - a) * (p - b) * (p - d1));

        a = DoubleTask.length(x3, y3, x4, y4);
        b = DoubleTask.length(x4, y4, x1, y1);
        p = (d1 + a + b) / 2;
        s += Math.sqrt(p * (p - a) * (p - b) * (p - d1));
        return s;
    }
}
