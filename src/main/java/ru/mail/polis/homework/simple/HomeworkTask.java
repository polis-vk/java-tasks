package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

import java.lang.Math;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integ = 0;
        for (double x = a; x <= b; x += delta) {
            integ += function.applyAsDouble(x) * delta;
        }
        return integ;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        if (Math.abs(a) <= 10) {
            return 1;
        }
        byte max = 0;
        byte pos = 0;
        byte exp = 0; 
        for (long i = 1; i < a; i *= 10) {
            byte cur = (byte) (Math.abs(a) % (i * 10) / i);
            if (cur >= max) {
                max = cur;
                pos = exp;
            }
          exp++;
        }
        pos = (byte) (exp - pos);
        return pos;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double y3 = 0;
        if (x1 != x2) {
            y3 = (double) (x3 - x1) / (x2 - x1) * (y2 - y1) + y1;
        }
        return y3;
    }

    /*
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения */
     
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double area124 = Math.abs(( (double) x1 - x4) * (y2 - y4) - (y1 - y4) * (x2 - x4)) / 2;
        double area324 = Math.abs(( (double) x3 - x4) * (y2 - y4) - (y3 - y4) * (x2 - x4)) / 2;
        return area124 + area324;
    }
}
