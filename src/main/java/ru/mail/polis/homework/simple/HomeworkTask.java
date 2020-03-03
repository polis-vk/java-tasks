package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integral = 0.0;
        for (double tmpX = a; tmpX < b; tmpX += delta) {
            integral += function.applyAsDouble(tmpX);
        }
        return integral * delta;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long max = 0;
        byte numberOfMax = 1;
        for (long tempNum = Math.abs(a); tempNum != 0; tempNum /= 10) {
            max = Math.max(max, tempNum % 10);
            numberOfMax = max == tempNum % 10 ? 1 : ++numberOfMax;
        }
        return numberOfMax;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (double) (x3 - x1) * (y2 - y1) / (x2 - x1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return (Math.abs(x1 * y2 + x2 * y3 + x3 * y1 - x2 * y1 - x3 * y2 - x1 * y3)
                + Math.abs(x4 * y2 + x2 * y3 + x3 * y4 - x2 * y4 - x3 * y2 - x4 * y3)
                + Math.abs(x4 * y1 + x1 * y3 + x3 * y4 - x1 * y4 - x3 * y1 - x4 * y3)
                + Math.abs(x4 * y1 + x1 * y2 + x2 * y4 - x1 * y4 - x2 * y1 - x4 * y2))
                / 4.0;
    }
}
