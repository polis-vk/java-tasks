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
        return (1 + n) * n / 2;
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        int days;
        if (top <= bottom && top < height) {
            days = Integer.MAX_VALUE;
            return days;
        }
        if (top >= height) {
            days = 1;
            return days;
        }
        if ((height - bottom) % (top - bottom) == 0) {
            days = (height - bottom) / (top - bottom);
        } else {
            days = (height - bottom) / (top - bottom) + 1;
        }
        return days;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int factor = (int) Math.pow(10, order);
        int remainder;
        int wholePart;
        if (order > 1) {
            remainder = n % factor;
            wholePart = remainder / (factor / 10);
        } else {
            remainder = n % factor;
            wholePart = remainder;
        }
        wholePart = Math.abs(wholePart);
        return wholePart;
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long fact = 1;
        for (int i = 0; i < n; i++) {
            fact = fact * (i + 1);
        }
        return fact;
    }
}
