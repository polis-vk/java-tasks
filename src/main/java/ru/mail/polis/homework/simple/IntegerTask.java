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
        int summ = 0;
        for (int i = 1; i < n; i++) {
            summ += i;
        }
        return summ;
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        int currentHeight = height;
        if (top < height && top <= bottom) {
            return Integer.MAX_VALUE;
        }
        int days = 0;
        while (currentHeight >= 0) {
            currentHeight -= top;
            days += 1;
            if (currentHeight <= 0) {
                break;
            }
            currentHeight += bottom;
        }
        return days;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int delimeter = 1;
        for (int i = 0; i < order; i++) {
            delimeter *= 10;
        }
        return Math.abs(n % delimeter / (delimeter / 10));
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long mult = 1;
        for (byte i = 1; i <= n; i++) {
            mult *= i;
        }
        return mult;
    }
}
