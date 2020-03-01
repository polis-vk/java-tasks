package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double itrl = 0;
        for (double i = a; i < b; i += delta) {
            itrl += function.applyAsDouble(i) * delta;
        }
        return itrl;
    }


    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte currLenIdx = 0; // общая длина цифры. начинается справа налево
        byte currMaxIdx = 1; // текущий рассматриваемый индекс макисмальной цифры. начинаем с единицы
        byte max = 0; // значение максимальной цифры
        long currDigit = 0; // счетчик

        if (a == 0) {
            currLenIdx = 1;
            currMaxIdx = 0;
        }

        while (a > 0) {
            currDigit = a % 10;
            if (currDigit >= max) {
                currMaxIdx = currLenIdx;
                max = (byte) currDigit;
            }
            a = a / 10;
            currLenIdx++;
        }
        return (byte) (currLenIdx - currMaxIdx);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (double) (y2 * (x1 - x3) + y1 * (x3 - x2)) / (x1 - x2);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        // https://www.webmath.ru/poleznoe/formules_13_9.php

        double d1 = Math.sqrt((x1 - x3) *(x1 - x3) + (y1 - y3) * (y1 - y3)); // длина диагонали
        double d1X = x1-x3; // координата вектора X диагонали
        double d1Y = y1-y3; // координата вектора Y диагонали

        double d2 = Math.sqrt((x2 - x4) * (x2 - x4) + (y2 - y4) * (y2 - y4));
        double d2X = x2-x4;
        double d2Y = y2-y4;

        if (d1 == 0 || d2 == 0) {
            return 0;
        }

        double cos = (d1X * d2X + d1Y * d2Y) / (Math.sqrt(d1X*d1X + d1Y*d1Y) * Math.sqrt(d2X*d2X + d2Y*d2Y));
        double sin = Math.sqrt(1 - cos*cos);

        return d1*d2*sin*0.5;
    }

}
