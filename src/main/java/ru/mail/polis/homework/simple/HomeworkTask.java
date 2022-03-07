package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

import static java.lang.Math.abs;
import static java.lang.Math.sqrt;
import static java.lang.Math.pow;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double sum = 0.0;
        for (double i = a; i <= b; i += delta) {
            sum += function.applyAsDouble(i);
        }
        return sum * delta;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     * @return
     */
    public static byte maxNumber(long a) {
        long max = 0;
        byte maxNum = 0;
        byte size = 0;
        long a1 = a;
        while (a1 != 0) {
            size++;
            long currentNum = abs(a1 % 10);
            if (currentNum >= max) {
                max = currentNum;
                maxNum = size;
            }
            a1 /= 10;
        }
        return ((byte) (size - maxNum + 1));
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double a = y1 - y2;
        double b = x2 - x1;
        double c = x1 * y2 - x2 * y1;
        return ((-a * x3 - c) / b);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double a = sqrt(pow(x2 - x1, 2) + pow(y2 - y1, 2));
        double b = sqrt(pow(x2 - x3, 2) + pow(y2 - y3, 2));
        double c = sqrt(pow(x4 - x3, 2) + pow(y4 - y3, 2));
        double d = sqrt(pow(x4 - x1, 2) + pow(y4 - y1, 2));
        double f = sqrt(pow(x3 - x1, 2) + pow(y3 - y1, 2));
        double p1 = (a + b + f) / 2;
        double p2 = (c + d + f) / 2;
        return (sqrt(p1 * (p1 - a) * (p1 - b) * (p1 - f)) + sqrt(p2 * (p2 - c) * (p2 - d) * (p2 - f)));
    }

}
