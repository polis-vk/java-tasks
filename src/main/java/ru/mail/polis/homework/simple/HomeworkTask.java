package ru.mail.polis.homework.simple;

import java.math.BigDecimal;
import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double answer = 0;
        for(int i = 0; i < (b-a)/delta; ++i)
        {
            answer += delta*function.applyAsDouble(a+i*delta);
        }
        return answer;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        int pos = 0, curr = 0, size = 0, ans = Integer.MIN_VALUE;
        while(a > 0)
        {
            ++curr;
            if(a%10 >= ans) {ans = (int) (a%10); pos = curr;}
            a /= 10;
        }
        return (byte)(curr+1-pos);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой, что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double up = (y2-y1)*(x3-x1);
        double down = x2-x1;
        double fraction = up/down;
        return fraction+y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double area = 0;
        int[] x = new int[4];
        int[] y = new int[4];
        int xi = 0, yi = 0, j = 3;
        x[xi++] = x1; y[yi++] = y1;
        x[xi++] = x2; y[yi++] = y2;
        x[xi++] = x3; y[yi++] = y3;
        x[xi] = x4; y[yi] = y4;
        for(int i = 0; i < 4; ++i)
        {
            area += ((x[j]+x[i])*(y[j]-y[i]));
            j = i;
        }
        return Math.abs(area/2.0);
    }

}
