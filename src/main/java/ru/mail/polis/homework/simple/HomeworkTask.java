package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integral = 0, interval = a;
        do{
            integral +=  (function.applyAsDouble(interval) * delta);
            interval += delta;
        } while(interval < b);
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte max_number_rank = 0;
        long max_number = 0;
        byte rank = 0;
        for (long a_rank = a; a_rank >= 1; ++rank){
            a_rank /= 10;
        }
        if (rank < 1){
            return 1;
        }
        for (byte i = 1; i <= rank; ++i) {
            long new_number = (long) ((a / Math.pow(10, (rank - i))) % 10);
            if (new_number > max_number) {
                max_number_rank = i;
                max_number = new_number;
            }
        }
        return max_number_rank;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double k = ((double) y2 - y1) / ((double) x2 - x1);
        double y0 = y1 - k * x1;
        return k * x3 + y0;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double s1  = (1./2) * Math.abs( (x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1) );
        double s2  = (1./2) * Math.abs( (x4 - x1) * (y3 - y1) - (x3 - x1) * (y4 - y1) );
        return s1 + s2;
    }

}
