package ru.mail.polis.homework.simple;

import java.lang.Math.*;

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
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        if (top >= height) {
            return 1;
        }
        if (bottom - top >= 0) {
            return Integer.MAX_VALUE;
        }
        int rest = height;
        int days = 0;
        while (rest > 0) {
            ++days;
            rest -= top;
            if (rest > 0) {
                rest += bottom;
            }
        }
        return days;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int number = n;
        int current_order = 1;
        while (current_order < order) {
            number = number / 10;
            ++current_order;
        }
        return Math.abs(number) % 10;
    }

    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long fact = 1;
        for (byte i = 1; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }
}
