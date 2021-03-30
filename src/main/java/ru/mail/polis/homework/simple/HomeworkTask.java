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
        for (; a <= b; a += delta) {
            sum += function.applyAsDouble(a) * (delta);
        }
        return sum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {

        int length = String.valueOf(Math.abs(a)).length();
        byte degree_max_number = 1;
        int number;
        int top = 0;
        for (byte i = 1; (length >= 0); length--) {
            number = (int) (a / Math.pow(10, length - 1)) % 10;
            if (number > top) {
                top = number;
                degree_max_number = i;
            }
            i++;
        }
        return degree_max_number;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return ((((x3 - x1) / (double) (x2 - x1)) * (y2 - y1)) + y1);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double side1 = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        double side2 = Math.sqrt(Math.pow((x3 - x2), 2) + Math.pow((y3 - y2), 2));
        double side3 = Math.sqrt(Math.pow((x4 - x3), 2) + Math.pow((y4 - y3), 2));
        double side4 = Math.sqrt(Math.pow((x1 - x4), 2) + Math.pow((y1 - y4), 2));

        double diagonal = Math.sqrt(Math.pow((x4 - x2), 2) + Math.pow((y4 - y2), 2));

        double half_meter_firstTriangle = (side1 + side4 + diagonal) * 0.5;
        double half_meter_secondTriangle = (side3 + side2 + diagonal) * 0.5;

        double area_firstTriangle = Math.sqrt(half_meter_firstTriangle * (half_meter_firstTriangle - side1) * (half_meter_firstTriangle - side4) * (half_meter_firstTriangle - diagonal));
        double area_secondTriangle = Math.sqrt(half_meter_secondTriangle * (half_meter_secondTriangle - side3) * (half_meter_secondTriangle - side2) * (half_meter_secondTriangle - diagonal));

        return (area_firstTriangle + area_secondTriangle);
    }
}