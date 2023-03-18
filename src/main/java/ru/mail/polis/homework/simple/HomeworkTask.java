package ru.mail.polis.homework.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double n = (b-a)/delta;
        double h = (b - a) / (n), sum = 0;
        for (double x = a; x <= b; x += h) {
                sum += function.applyAsDouble(x);
            }
            return h * sum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long max = -1;
        long maxIndex = 1;
        ArrayList<Long> numbers = new ArrayList<>();
        long a_1 = a;
        while (a_1 >= 1){
            numbers.add(a % 10);
            a_1 /= 10;
        }
        Collections.reverse(numbers);
        for(int i = 0; i < numbers.size(); i++) {
            if (max <  numbers.get(i)) {
                max = numbers.get(i);
                maxIndex = i + 1;
            }
        }
        return (byte) maxIndex;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (double) (x3 - x1) * (y2 - y1) / (x2 - x1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        int line_1 = (x1 - x2) * (y1 + y2);
        int line_2 = (x2 - x3) * (y2 + y3);
        int line_3 = (x3 - x4) * (y3 + y4);
        int line_4 = (x4 - x1) * (y4 + y1);
        return (double) (Math.abs(line_1 + line_2 + line_3 + line_4))/ 2;
    }

}
