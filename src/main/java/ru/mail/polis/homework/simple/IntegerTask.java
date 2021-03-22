package ru.mail.polis.homework.simple;

import java.lang.Math;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerTask {

    /**
     * Сумма чисел от 1 до n (1 + 2 + 3 + ... + n)
     * Пример: (5) -> 15
     */
    public static int sum(int n) {
        int s = 0;

        for (int i = n; i > 0; i -= 1) {
            s += i;
        }
        return s;
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        int days = 1;
        
        if (bottom >= top && top < height) {
            return Integer.MAX_VALUE;
        }
        else {
            for (int curDist = 0; curDist < height; curDist -= bottom) {
                curDist += top;
                if (curDist >= height) {
                    return days;
                }
                days++;
            }
            return Integer.MAX_VALUE;
        }
        
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int num = 0;

        num = Math.abs(n) % (int) Math.pow(10, order);
        num = num /  (int) Math.pow(10, order - 1);

        return num;
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long f = n;

        if (n == 0) {
            n = 1;
            return n;
        }
        for (int i = n - 1; i > 0; i--) {
            f *= i;
        }
        return f;
    }
}