package ru.mail.polis.homework.simple;


import java.util.ArrayList;
import java.util.List;

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
        int res = 0;
        for (int i = 1; i <= n; i += 1) {
            res += i;
        }

        return res;
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        if (top <= bottom && top < height) {
            return Integer.MAX_VALUE;
        }
        int x = 0;
        int count = 0;
        while (x + top < height) {
            x += (top - bottom);
            count += 1;
        }
        return count + 1;

    }


    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int num = n;
        int index = 0;
        int res = 0;
        if (num < 0) {
            num = num * -1;
        }
        while (num != 0) {

            index += 1;
            if (index == order) {
                res = num % 10;
            }
            num /= 10;
        }
        return res;
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long res = 1;
        if (n == 0) {
            return res;
        }
        for (int i = 1; i <= n; i += 1) {
            res *= i;
        }
        return res;
    }
}
