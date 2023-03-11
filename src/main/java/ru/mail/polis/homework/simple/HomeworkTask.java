package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    private static double lenDots(int x1, int y1, int x2, int y2) {
        return Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
    }

    private static double squareTriangle(double A, double B, double C) {
        double p1 = (A + B + C) / 2;
        return Math.sqrt(p1 * (p1 - A) * (p1 - B) * (p1 - C));
    }

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {

        double sum = 0;
        for (double i = a; i <= b; i += delta) {
            sum += delta * function.applyAsDouble(i);
        }
        return sum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte maxNumber = -1;
        byte lenNumber = 0;
        byte number = 0;
        while (a != 0) {
            lenNumber += 1;
            if (Math.abs(a % 10) >= maxNumber) {
                maxNumber = (byte) Math.abs((a % 10));
                number = lenNumber;
            }
            a = a / 10;
        }
        return (byte) (lenNumber - number + 1);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double k = (double) (y1 - y2) / (x1 - x2);
        double b = (double) y1 - k * x1;
        return k * x3 + b;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double A = lenDots(x1, y1, x4, y4);
        double B = lenDots(x1, y1, x2, y2);
        double C = lenDots(x2, y2, x3, y3);
        double D = lenDots(x3, y3, x4, y4);
        double diag = lenDots(x1, y1, x3, y3);


        double S1 = squareTriangle(A, D, diag);
        double S2 = squareTriangle(B, C, diag);
        return S1 + S2;
    }

}
