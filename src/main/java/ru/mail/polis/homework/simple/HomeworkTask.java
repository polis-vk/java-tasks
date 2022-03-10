package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;


public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integral_value = 0.0;
        double c = a;

        while (b > c) {
            integral_value += function.applyAsDouble(c) * delta;
            c += (delta);

        }

        return integral_value;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long number = a;
        int count = 0;
        int Max_digit = 0;
        int index = 0;

        while (number > 0) {
            count++;
            if (Max_digit <= (number % 10)) {
                index = count;
                Max_digit = (int) number % 10;
            }
            number /= 10;
        }
        return (byte) (count - index + 1);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        if (x1 != x2) {
            return (((double) (y2 - y1) * (x3 - x1) / (double) (x2 - x1)) + y1);
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
