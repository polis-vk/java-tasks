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
        double quadrangleArea;
        while (a < b) {
            if (a + delta >= b) {
                quadrangleArea = (b - a) * function.applyAsDouble(a);
                integral += quadrangleArea;
            }
            quadrangleArea = delta * function.applyAsDouble(a);
            integral += quadrangleArea;
            a += delta;
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long reminder;
        long maxNumber = 0;
        long maxNumberPosition = 0;
        long numberLength = 0;
        if (a == 0)
            return (byte) 1;
        for (int i = 1; a != 0; a /= 10, i++) {
            reminder = Math.abs(a % 10);
            if (reminder >= maxNumber) {
                maxNumber = reminder;
                maxNumberPosition = i;
            }
            numberLength = i + 1;
        }
        return (byte) Math.abs(numberLength - maxNumberPosition);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double slope = (y2 - y1) * 1.0 / (x2 - x1);
        double rise = y1 - slope * x1;
        return slope * x3 + rise;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return 0.5 * Math.abs((x1 - x2) * (y1 + y2) + (x2 - x3) * (y2 + y3) + (x3 - x4) * (y3 + y4) + (x4 - x1) * (y4 + y1));
    }

}
