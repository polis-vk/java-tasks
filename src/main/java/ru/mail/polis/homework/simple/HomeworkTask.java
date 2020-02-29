package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integr = 0.0;
        while (a < b){
            integr += function.applyAsDouble(a) * delta;
            a += delta;
        }
        return integr;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte num = 0, k = 0, n = 0;
        while (a > 0){
            n++;
            if (a % 10 >= num){
                num = (byte) (a % 10);
                k = n;
            }
            a /= 10;
        }
        return (byte) (n - k + 1);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double k = (y2 - y1) * 1.0 /(x2 - x1);
        return y2 + k * (x3 - x2);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double l1, l2, l3, l4, d, s1, s2, p1, p2;
        l1 = Math.sqrt((Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2)));
        l2 = Math.sqrt((Math.pow((x3 - x2), 2) + Math.pow((y3 - y2), 2)));
        l3 = Math.sqrt((Math.pow((x4 - x3), 2) + Math.pow((y4 - y3), 2)));
        l4 = Math.sqrt((Math.pow((x1 - x4), 2) + Math.pow((y1 - y4), 2)));
        d = Math.sqrt((Math.pow((x3 - x1), 2) + Math.pow((y3 - y1), 2)));
        p1 = 0.5 * (l1 + l2 + d);
        p2 = 0.5 * (l3 + l4 + d);
        s1 = Math.sqrt(p1 * (p1 - l1) * (p1 - l2) * (p1 - d));
        s2 = Math.sqrt(p2 * (p2 - l3) * (p2 - l4) * (p2 - d));
        return s1 + s2;
    }

}
