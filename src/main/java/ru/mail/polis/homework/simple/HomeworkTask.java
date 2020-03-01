package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        function.applyAsDouble(4d);
        double sum = 0;
        for (double i = a; i < b; i += delta) {
            sum += delta * function.applyAsDouble(i);
        }
        return sum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long max = a % 10;
        a = a / 10;
        int count = 1;
        int position = 0;
        while (a > 0) {
            if (a % 10 >= max) {
                max = a % 10;
                position = count;
            }
            a = a / 10;
            count += 1;
        }
        return (byte) (count - position);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double y3 = (double) ((y2 - y1) * (x3 - x1)) / (x2 - x1) + y1;
        return y3;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double square = 0.5 * Math.abs((x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1)) + 0.5 * Math.abs((x4 - x1) * (y3 - y1) - (y4 - y1) * (x3 - x1));
        return square;
    }

}
