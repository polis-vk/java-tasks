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
        double pos = a;
        double res = 0;
        while (pos + delta < b) {
            res += (function.applyAsDouble(pos) + function.applyAsDouble(pos + delta)) * delta / 2;
            pos += delta;
        }
        res += (function.applyAsDouble(pos) + function.applyAsDouble(b)) * (b - pos) / 2;
        return res;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte maxIndex = 1;
        byte maxNum = 0;
        byte i = 0;
        do {
            i++;
            if (a % 10 >= maxNum) {
                maxIndex = i;
                maxNum = (byte) (a % 10);
            }
            a = a / 10;
        } while (a > 0);
        return (byte) (i - maxIndex + 1);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double speed = ((double) y2 - (double) y1) / ((double) x2 - (double) x1);
        return y2 + speed * ((double) x3 - (double) x2);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    // введем похожую на DoubleTask.length функцию, которая возвращает double
    public static double length(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
    }

    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double d1 = length(x1, y1, x3, y3); //нужна для подсчета диагоналей
        double d2 = length(x2, y2, x4, y4);
        if (d1 == 0d || d2 == 0d) {
            return 0;
        }
        double a1 = x3 - x1; // где (a1, b1) - вектор ab1
        double b1 = y3 - y1;
        double a2 = x4 - x2; // где (a2, b2) - вектор ab2
        double b2 = y4 - y2;
        // косинус угла между векторами
        double cos = (a1 * a2 + b1 * b2) / ((Math.sqrt(Math.pow(a1, 2) + Math.pow(b1, 2))) * (Math.sqrt(Math.pow(a2, 2) + Math.pow(b2, 2))));
        // используем основное тригонометрическое тождество
        double sin = Math.sqrt(1 - Math.pow(cos, 2));
        return d1 * d2 * sin / 2;
    }

}
