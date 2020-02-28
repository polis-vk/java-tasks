package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        function.applyAsDouble(4d);
        double square = 0;
        for (double i = a; i <= b; i += delta) {
            square += function.applyAsDouble(i) * delta;
        }
        return square;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        int count = 1;
        int ind = 0;
        long max = -1;
        while (a != 0) {
            long curr = a % 10;
            if (curr >= max) {
                max = curr;
                ind = count;
            }
            a /= 10;
            count++;
        }
        return (byte) (count - ind);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (double) (x1 * y2 - x2 * y1 + (y1 - y2) * x3) / (x1 - x2);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double AB = Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        double BC = Math.sqrt((x3 - x2) * (x3 - x2) + (y3 - y2) * (y3 - y2));
        double CD = Math.sqrt((x4 - x3) * (x4 - x3) + (y4 - y3) * (y4 - y3));
        double DA = Math.sqrt((x1 - x4) * (x1 - x4) + (y1 - y4) * (y1 - y4));
        double AC = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));
        double p1 = (AB + BC + AC)/2;
        double p2 = (CD + DA + AC)/2;
        return Math.sqrt(p1 * (p1 - AB) * (p1 - BC) * (p1 - AC)) + Math.sqrt(p2 * (p2 - CD) * (p2 - DA) * (p2 - AC));
    }

}
