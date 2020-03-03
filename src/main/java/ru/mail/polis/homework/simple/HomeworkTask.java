package ru.mail.polis.homework.simple;

import java.awt.*;
import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        function.applyAsDouble(4d);
        double square = 0;
        for (double x = a; x < b; x += delta) {
            square += delta * function.applyAsDouble(x);
        }
        return square;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        if (a == 0) {
            return 1;
        }
        long number = a;
        byte i = 1;
        byte j = 0;
        int maxDigit = 0;
        while (number != 0) {
            long digit = number % 10;
            if (maxDigit <= digit) {
                maxDigit = (int) digit;
                j = i;
            }
            number /= 10;
            i++;
        }
        return (byte) (i - j);
    }

    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double k = (double) (y2 - y1) / (x2 - x1);
        double b = y1 - (double) (y2 - y1) / (x2 - x1) * x1;
        return k * x3 + b;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        //Метод Гаусса
        final Point[] points = {
                new Point(x1, y1),
                new Point(x2, y2),
                new Point(x3, y3),
                new Point(x4, y4)
        };

        double ans = 0;
        for (int i = 0; i < points.length - 1; i++) {
            ans += points[i].x * points[i + 1].y;
            ans -= points[i + 1].x * points[i].y;
        }
        ans += points[points.length - 1].x * points[0].y;
        ans -= points[points.length - 1].y * points[0].x;

        ans = Math.abs(ans) / 2;
        return ans;
    }

}
