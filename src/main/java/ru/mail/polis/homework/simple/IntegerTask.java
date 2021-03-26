package ru.mail.polis.homework.simple;


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
        int amount = 0;
        if (n > 0) {
            for (int i = 1; i <= n; i++) {
                amount = amount + i;
            }
            return amount;
        }
        return 0;
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        int progress = 0;
        int days = 0;
        if (top >= height) {
            return 1;
        }
        if (top <= bottom) {
            return Integer.MAX_VALUE;
        }
        if (top > bottom) {
            while (progress < height - top) {
                progress += top;
                days++;
                progress -= bottom;
            }
            days++;
        }
        return days;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        if (n < 0) {
            n *= -1;
        }
        if (n == 10 && order == 1) {
            return 0;
        }
        if (n == 10 && order == 2) {
            return 1;
        }
        int result = n;
        for (int i = 0; i < order - 1; i++) {
            result = result / 10;
        }
        result %= 10;
        return result;
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long fact = 1;
        for (int i = 1; i <= n; i++) {
            fact = fact * i;
        }
        return fact;
    }
}
