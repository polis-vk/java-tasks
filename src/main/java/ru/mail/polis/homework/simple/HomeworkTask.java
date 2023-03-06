package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

import static java.lang.Math.sqrt;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integral = 0;

        for (double t = a; t < b; t += delta) {
            integral += function.applyAsDouble(t) * delta;
        }

        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long b = a;
        byte max = 0;
        byte pos = 1; // позиция максимальной цифры
        long digit;
        byte num;

        for (byte i = 1; b % 10 != 0; i++) {
            digit = (long) Math.pow(10, (byte) Math.log10(b));
            num = (byte) (b / digit);
            if (num == 9) return i;
            if (Math.abs(num) > max) {
                max = num;
                pos = i;
            }
            b = b % digit;
        }
        return pos;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {

        return (double) (x3 - x1) / (double) (x2 - x1) * (y2 - y1) + y1; // выразил у из уравнения прямой через две точки
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {

        double a = sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2)); // длина первой стороны четырехугольника
        double b = sqrt(Math.pow((x2 - x3), 2) + Math.pow((y2 - y3), 2)); // длина второй стороны четырехугольника
        double c = sqrt(Math.pow((x3 - x4), 2) + Math.pow((y3 - y4), 2)); // длина третьей стороны четырехугольника
        double d = sqrt(Math.pow((x4 - x1), 2) + Math.pow((y4 - y1), 2)); // длина четвертой стороны четырехугольника
        double diagonal = sqrt(Math.pow((x3 - x1), 2) + Math.pow((y3 - y1), 2)); // длина диагонали четырехугольника

        // Для нахождения площади разобьем четырехугольник на два треугольника.
        // Найдя их площадь по формуле Герона, сложим и получим площадь данного четырехугольника.

        double p1 = (a + b + diagonal) / 2; // полупериметр первого треугольника
        double p2 = (c + d + diagonal) / 2; // полупериметр второго треугольника

        double s1 = Math.sqrt(p1 * (p1 - a) * (p1 - b) * (p1 - diagonal)); // площадь первого треугольника
        double s2 = Math.sqrt(p2 * (p2 - c) * (p2 - d) * (p2 - diagonal)); // площадь первого треугольника

        return s1 + s2;
    }

}
