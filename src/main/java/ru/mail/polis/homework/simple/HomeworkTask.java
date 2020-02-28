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
        for (double i = a; i < b; i += delta){
            integral += function.applyAsDouble(i) * delta;
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        int maxValue = -1;
        byte maxIndex = -1;
        byte index = 0;
        byte value;
        for (long i = a; i > 0; i /= 10) {
            value = (byte) (i % 10);
            if (value >= maxValue) {
                maxIndex = index;
                maxValue = value;
            }
            index++;
        }
        return (byte) (index - maxIndex);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return 1.0 * (x3 - x1) * (y2 - y1) / (x2 - x1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return triangleSquare(x1, y1, x2, y2, x4, y4) + triangleSquare(x2, y2, x3, y3, x4, y4);
    }

    private static double triangleSquare(int x1, int y1, int x2, int y2, int x3, int y3){
        double lengthA = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double lengthB = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double lengthC = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));

        double p = (lengthA + lengthB + lengthC) / 2;
        return Math.sqrt(p * (p - lengthA) * (p - lengthB) * (p - lengthC));
    }

}
