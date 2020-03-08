package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double answer = 0;
        double x = a;
        while (x + delta < b) {
            x = x + delta;
            answer = (answer + (delta * function.applyAsDouble(x)));
        }
        return answer;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        int i = 1;
        int n = 0;
        int currentMax = (int) (a % Math.pow(10, 1));
        do {
            n++;
            if ((a % Math.pow(10, n) / Math.pow(10, n - 1)) >= currentMax) {
                currentMax = (int) (a % Math.pow(10, n) / Math.pow(10, n - 1));
                i = 1;
            } else {
                i = i + 1;
            }
        } while ((int) (a / Math.pow(10, n)) != 0);
        return (byte) i;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double k = ((double) (y2 - y1)) / ((double) (x2 - x1));
        double b = y2 - k * x2;
        return (k * x3 + b);
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
