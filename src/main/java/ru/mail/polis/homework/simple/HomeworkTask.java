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
        double current = a;
        while (current < b) {
            integral += function.applyAsDouble(current) * delta;
            current += delta;
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long firstNum = a;
        long num = 0;
        byte digitPosition = 0;
        byte digitCount = 0;
        while (firstNum > 0) {
            digitCount++;
            if (firstNum % 10 >= num) {
                num = firstNum % 10;
                digitPosition = digitCount;
            }
            firstNum /= 10;
        }
        return (byte) (digitCount - digitPosition + 1);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double k = (y2 - y1) * 1.0 / (x2 - x1);
        return y2 + k * (x3 - x2);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double l1 = Math.sqrt((Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));
        double l2 = Math.sqrt((Math.pow((x3 - x2), 2) + Math.pow((y3 - y2), 2)));
        double l3 = Math.sqrt((Math.pow((x4 - x3), 2) + Math.pow((y4 - y3), 2)));
        double l4 = Math.sqrt((Math.pow((x1 - x4), 2) + Math.pow((y1 - y4), 2)));
        double d = Math.sqrt((Math.pow((x3 - x1), 2) + Math.pow((y3 - y1), 2)));
        double p1 = 0.5 * (l1 + l2 + d);
        double p2 = 0.5 * (l3 + l4 + d);
        double s1 = Math.sqrt(p1 * (p1 - l1) * (p1 - l2) * (p1 - d));
        double s2 = Math.sqrt(p2 * (p2 - l3) * (p2 - l4) * (p2 - d));
        return s1 + s2;
    }

}
