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
        for (double index = a; index < b; index += delta){
            integral = integral + (delta * function.applyAsDouble(index));
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte position = 0;
        byte orders = 1;
        int max = 0;
        long number = a;
        if (a == Long.MIN_VALUE) {
            number = Long.MAX_VALUE;
        }
        else {
            number = Math.abs(a);
        }
        while (number > 0) {
            if (number % 10 >= max) {
                max = (int) (number % 10);
                position = orders;
            }
            number = number / 10;
            ++orders;
        }
        return (byte) (orders - position);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        int x2_copy = x2;
        int y2_copy = y2;
        int x3_copy = x3;
        double numerator = (y2_copy - y1) * (x3_copy - x1);
        double y3 = numerator / (x2_copy - x1) + y1;
        return y3;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double s = 0.5 * Math.abs((x1 * y2 + x2 * y3 + x3 * y4 + x4 * y1 - x2 * y1 - x3 * y2 - x4 * y3 - x1 * y4));;
        return s;
    }

}
