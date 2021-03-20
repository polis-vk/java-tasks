package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {
    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double result = 0;
        for (double x = a; x < b - (delta / 2); x += delta) {
            result += 0.5 * delta * (function.applyAsDouble(x) + function.applyAsDouble(x + delta));
        }

        return result;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        if (a == 0) {
            return 1;
        }

        int max = 0;
        int pos = 0;
        int length = 0;

        long aCopy = a;
        while (aCopy != 0) {
            int digit = (int) (aCopy % 10);

            if (digit >= max) {
                max = digit;
                pos = length;
            }
            aCopy /= 10;
            ++length;
        }

        return (byte) (length - pos);
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
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return Math.abs((double) (x1 - x2) * (y3 - y2) - (y1 - y2) * (x3 - x2)) / 2 +
                Math.abs((double) (x1 - x3) * (y4 - y3) - (y1 - y3) * (x4 - x3)) / 2;
    }

}
