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
        for (double step = a; step <= b; step += delta) {
            sum += delta * function.applyAsDouble(step);
        }
        return sum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long acc = a;
        int len = (int) Math.ceil(Math.log10(acc));
        long max = -1;
        byte maxIndex = 1;
        long tmp, order;
        for (byte i = 1; i <= len; i++) {
            order = (long) Math.pow(10, len - i);
            tmp = acc / order;
            if (tmp > max) {
                max = tmp;
                maxIndex = i;
                if (max == 9) {
                    break;
                }
            }
            acc %= order;
        }
        return maxIndex;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        // Каноническое уравнение прямой (X - x1) / (x2 - x1) = (Y - y1) / (y2 - y1),
        // найдем Y подставив x3 в X
        return ((double) x3 - x1) / (x2 - x1) * (y2 - y1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        // Формула площади Гаусса для четырехуголька
        return (double) Math.abs(x1 * y2 + x2 * y3 + x3 * y4 + x4 * y1 - x2 * y1 - x3 * y2 - x4 * y3 - x1 * y4) / 2;
    }

}
