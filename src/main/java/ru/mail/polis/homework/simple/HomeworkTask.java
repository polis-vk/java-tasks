package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integral = 0;
        for (double i = a; i < b; i += delta)
        {
            integral += function.applyAsDouble(i) * delta;
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte max = 0;
        long number = a;
        int pos = 1;
        int count = 0;
        while (number > 0)
        {
            if (number % 10 >= max)
            {
                max = (byte)(number % 10);
                pos = count;
            }
            number /= 10;
            count++;
        }
        pos = Math.abs(count - pos);
        return (byte)pos;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        //Формула линейной интерполяции
        double y3 = ((double)(x3 - x1) / (double)(x2 - x1)) * (double)(y2 - y1) + y1;
        return y3;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double side1 = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double side2 = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double side3 = Math.sqrt(Math.pow(x3 - x4, 2) + Math.pow(y3 - y4, 2));
        double side4 = Math.sqrt(Math.pow(x4 - x1, 2) + Math.pow(y4 - y1, 2));
        double diagonal = Math.sqrt(Math.pow(x2 - x4, 2) + Math.pow(y2 - y4, 2));
        double semiPerimeter1 = (side1 + diagonal + side4) / 2.0;
        double semiPerimeter2 = (side2 + diagonal + side3) / 2.0;
        double triangleSquare1 = Math.sqrt(semiPerimeter1 * (semiPerimeter1 - side1) * (semiPerimeter1 - diagonal) * (semiPerimeter1 - side4));
        double triangleSquare2 = Math.sqrt(semiPerimeter2 * (semiPerimeter2 - side2) * (semiPerimeter2 - diagonal) * (semiPerimeter2 - side3));
        double quadrilateralSquare = triangleSquare1 + triangleSquare2;
        return quadrilateralSquare;
    }

}
