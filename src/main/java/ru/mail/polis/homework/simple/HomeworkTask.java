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
        double valueOfIntegral = 0;
        //Метод прямоугольников
        while (Double.compare(a, b) < 0) {
            valueOfIntegral += function.applyAsDouble(a) * delta;
            a += delta;
        }
        return valueOfIntegral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte numberOfdigits = 1; //количество цифр в числе

        byte number = (byte) Math.abs(a % 10); //очередная цифра числа
        byte order = 1; //номер очередной цифры справа налево

        byte max = number; //наибольшая цифра
        byte maxOrder = 1; //номер максимальной цифры

        a /= 10;
        while (a != 0) {
            numberOfdigits++;
            order++;
            number = (byte) Math.abs(a % 10);
            if (number >= max) {
                max = number;
                maxOrder = order;
            }
            a /= 10;
        }
        return (byte) (numberOfdigits + 1 - maxOrder);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        //Уравнение прямой вида: y = ax + b
        double a = (double) (y2 - y1) / (x2 - x1);
        double b = y1 - a * x1;
        return a * x3 + b;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        //Уравнение прямой вида: y = ax + b
        double a = (double) (y2 - y1) / (x2 - x1);
        double b = y1 - a * x1;

        //Решение заключается в поиске диагонали и разбиении четырехугольника на два треугольника
        double s1, s2;
        s1 = Math.abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1)) / 2.0;
        if (Double.compare(Math.abs(a * x3 + b - y3), 0.0) * Double.compare(Math.abs(a * x4 + b - y4), 0.0) != 1) {
            s2 = Math.abs((x2 - x1) * (y4 - y1) - (x4 - x1) * (y2 - y1)) / 2.0;
            return s1 + s2;
        }

        a = (double) (y3 - y2) / (x3 - x2);
        b = y2 - a * x2;
        if (Double.compare(Math.abs(a * x1 + b - y1), 0.0) * Double.compare(Math.abs(a * x4 + b - y4), 0.0) != 1) {
            s2 = Math.abs((x2 - x3) * (y4 - y3) - (x4 - x3) * (y2 - y3)) / 2.0;
            return s1 + s2;
        }

        s2 = Math.abs((x1 - x3) * (y4 - y3) - (x4 - x3) * (y1 - y3)) / 2.0;
        return s1 + s2;
    }
}
