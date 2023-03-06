package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {

        double sum = 0.0;
        for (double x = a + delta / 2; x < b; x += delta) {
            sum += function.applyAsDouble(x) * delta;
        }
        return sum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long number = a; // в комментарии бало сказано "изменять входные переменные плохой тон"
        int maxDigit = 0;
        int maxDigitIndex = 0;
        int index = 1;
        while (number > 0) {
            int digit = (int) (number % 10);
            if (digit > maxDigit) {
                maxDigit = digit;
                maxDigitIndex = index;
            }
            number /= 10L;
            index++;
        }
        return (byte) (index - maxDigitIndex);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {

        double y3 = y1 + (double) ((y2 - y1) * (x3 - x1)) / (double) (x2 - x1);
        return y3;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        //Формула Герона для вычисления площади треугольника
        // (Разбиваем четырехугольник на два треугольника и складываем их площади)
        double AB = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double BC = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double CD = Math.sqrt(Math.pow(x4 - x3, 2) + Math.pow(y4 - y3, 2));
        double DA = Math.sqrt(Math.pow(x1 - x4, 2) + Math.pow(y1 - y4, 2));
        double AC = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
        double p1 = (AB + BC + AC) / 2;
        double p2 = (CD + DA + AC) / 2;
        double area = Math.sqrt(p1 * (p1 - AB) * (p1 - BC) * (p1 - AC))
                + Math.sqrt(p2 * (p2 - CD) * (p2 - DA) * (p2 - AC));
        return area;
    }

}
