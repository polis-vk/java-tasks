package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double res = 0.0;
        for (double x = a; x < b; x += delta) {
            res += delta * function.applyAsDouble(x);
        }
        return res;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte num = (byte) ((int) (Math.log10(a)) + 1);
        byte index = num;
        byte max = 0;

        for (byte i = 1; i <= num; i++) {
            byte current = (byte) (a / Math.pow(10, num - i) % 10);
            if (current == 9) return i;
            if (current > max) {
                max = current;
                index = i;
            }
        }
        return index;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return y1 + ((x3 - x1) * (y2 - y1) / (double) (x2 - x1));
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double AC = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));
        double BD = Math.sqrt(Math.pow(x2 - x4, 2) + Math.pow(y2 - y4, 2));

        if (AC == 0 || BD == 0) return 0;

        return 0.5 * AC * BD * Math.abs((x3 - x1) * (y4 - y2) - (y3 - y1) * (x4 - x2)) / (AC * BD);
    }
}
