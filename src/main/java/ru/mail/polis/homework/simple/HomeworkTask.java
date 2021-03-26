package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double sum = 0;
        while ((a + delta) <= b) {
            sum += delta * function.applyAsDouble(a + delta);
            a += delta;
        }

        return sum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        int number_counter = 1;
        long number_copy = Math.abs(a);
        byte maxIndex = 0;
        long max = 0;
        while ((number_copy /= 10) != 0) {
            number_counter++;
        }
        for (byte index = 1; index <= number_counter; index++) {
            if ((a % 10) >= max) {
                max = a % 10;
                maxIndex = index;
            }
            a /= 10;

        }

        return (byte) (number_counter - (maxIndex - 1));
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {

        return (((double) (y2 - y1) * (x3 - x1)) / (x2 - x1)) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double firstTriangle = Math.abs((x1 - x2) * (y3 - y2) - (y1 - y2) * (double) (x3 - x2)) / 2;
        double secondTriangle = Math.abs((x1 - x3) * (y4 - y3) - (y1 - y3) * (double) (x4 - x3)) / 2;
        return firstTriangle + secondTriangle;
    }
}
