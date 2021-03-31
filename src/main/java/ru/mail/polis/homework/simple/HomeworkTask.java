package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double resultCalc = 0;
        for (int i = 0; i < (b - a) / delta; i++) {
            resultCalc += function.applyAsDouble(delta * i + a);
        }
        return resultCalc * delta;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte result = 0;
        long b = a;
        while (b != 0) {
            long current = b % 10;
            if (current > result)
                result = (byte) current;
            b /= 10;
        }
        return result;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        if (x1 == x2 && y1 == y2) {
            System.out.println("Invalid coordinates");
            return 0;
        }

        return (double)((x3 - x1) * (y2 - y1)) / (x2 - x1) + y1;

    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double s1 = DoubleTask.length(x1, y1, x2, y2);
        double s2 = DoubleTask.length(x2, y2, x3, y3);
        double s3 = DoubleTask.length(x3, y3, x4, y4);
        double s4 = DoubleTask.length(x4, y4, x1, y1);

        double diagonal = DoubleTask.length(x1, y1, x3, y3);

        double perimeter1 = (s1 + s2 + diagonal) / 2;
        double perimeter2 = (s3 + s4 + diagonal) / 2;

        double square1 = Math.sqrt(perimeter1 * (perimeter1 - s1) * (perimeter1 - s2) * (perimeter1 - diagonal));
        double square2 = Math.sqrt(perimeter2 * (perimeter2 - s3) * (perimeter2 - s4) * (perimeter2 - diagonal));

        return square1 + square2;
    }

}
