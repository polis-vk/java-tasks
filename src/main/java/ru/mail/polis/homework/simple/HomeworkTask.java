package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double sum = 0;
        double left = a;

        for (double i = a; i <= b; i += delta) {
            double left_y = function.applyAsDouble(left);
            double i_y = function.applyAsDouble(i);
            sum += delta * left_y;
            sum += delta * (i_y - left_y) / 2;
            left = i;
        }

        return sum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte ind = -1;
        long ma = -1;
        byte co = 0;
        while (a != 0) {
            int num = (int) Math.abs(a % 10);
            if (num > ma) {
                ma = num;
                ind = co;
            } else if (num == ma) {
                ind = co;
            }
            a /= 10;
            co++;
        }
        return (byte) (co - ind);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double del_x = (x2 - x1);
        double del_y = (y2 - y1);
        if (del_x == 0) {
            return Double.MIN_VALUE;
        }
        double k = (x3 - x1) / del_x;
        return y1 + k * del_y;

    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {

        double ab_x = x2 - x1;
        double ab_y = y2 - y1;

        double ac_x = x3 - x1;
        double ac_y = y3 - y1;

        double ad_x = x4 - x1;
        double ad_y = y4 - y1;

        double s_abc = (ab_x * ac_y - ab_y * ac_x) / 2;
        if (s_abc < 0) {
            s_abc *= -1;
        }

        double s_acd = (ad_x * ac_y - ad_y * ac_x) / 2;
        if (s_acd < 0) {
            s_acd *= -1;
        }

        return s_abc + s_acd;

    }

}
