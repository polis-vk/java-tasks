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
        if (height <= top) {
            return 1;
        }
        if ((top - bottom) <= 0) {
            return Integer.MAX_VALUE;
        }
        if ((height - bottom) % (top - bottom) == 0) { // если число дней будет не целым, нам нужно добавить еще один день, так как деление целочисленное.
            return (height - bottom) / (top - bottom);
        } else
            return (height - bottom) / (top - bottom) + 1;
    }

    /**
     * Дано число a и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int a = n;
        for (int i = 1; i < order; i++) {
            a = a / 10;
        }

        return a >= 10 || a<=-10 ? Math.abs(a) % 10 : a;
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        return n >= 1 ? n * factorial((byte) (n - 1)) : 1;
    }
}
