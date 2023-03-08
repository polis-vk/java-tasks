package ru.mail.polis.homework.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double sum = 0;
        double x = a;
        while (x <= b) {
            sum += function.applyAsDouble(x);
            x += delta;
        }
        return delta * sum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        ArrayList<Integer> Arr = new ArrayList<>();
        while (a > 0) {
            Arr.add((int) a % 10);
            a /= 10;
        }
        Collections.reverse(Arr);
        int max = 0;
        byte kmax = 1;
        for (int i = 0; i < Arr.size(); i++) {
            if (Arr.get(i) > max) {
                max = Arr.get(i);
                kmax = (byte) (i + 1);
            }
        }
        return kmax;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (double) (x3 - x1) / (x2 - x1) * (y2 - y1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return (double) Math.abs((x1 - x2) * (y1 + y2) + (x2 - x3) * (y2 + y3) + (x3 - x4) * (y3 + y4) + (x4 - x1) * (y4 + y1)) / 2;
    }

}
