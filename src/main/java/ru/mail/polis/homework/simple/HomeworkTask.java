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
        double copyA = a;
        double valueOfIntegral = 0;
        //Метод прямоугольников
        while (Double.compare(copyA, b) < 0) {
            valueOfIntegral += function.applyAsDouble(copyA) * delta;
            copyA += delta;
        }
        return valueOfIntegral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        if (a < 10) {
            return 1;
        }

        byte numberOfDigits = (byte) (Math.log10(a) + 1); //количество цифр в числе
        long pow10 = (long) Math.pow(10, numberOfDigits - 1); //степень десяти для выделения очередной цифры числа

        byte number = (byte) (a / pow10); //очередная цифра числа
        pow10 /= 10;

        byte maxNumber = number; //наибольшая цифра
        byte maxOrder = 1; //номер максимальной цифры

        for (byte i = 1; i < numberOfDigits; i++) {
            number = (byte) (a / pow10 % 10);
            if (number > maxNumber) {
                maxNumber = number;
                maxOrder = (byte) (i + 1);
            }
            if (maxNumber == 9) {
                break;
            }
            pow10 /= 10;
        }
        return maxOrder;
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
        //Решение заключается в разбиении четырехугольника на два треугольника
        double s1 = Math.abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1)) / 2.0; //Площадь треугольника ABC
        double s2 = Math.abs((x1 - x3) * (y4 - y3) - (x4 - x3) * (y1 - y3)) / 2.0; //Площадь треугольника ACD
        return s1 + s2;
    }
}
