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
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum = i + sum;
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
        int x = 0;
        int days = 0;
        if (top < height) {
            if (top > bottom) {
                while (x < height - bottom) {
                    x = x + top - bottom;
                    days++;
                }
            } else {
                days = Integer.MAX_VALUE;
            }
        } else {
            days = 1;
        }

        return days;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int modTens = 1;
        for (int i = 1; i < order; i++) {
            modTens *= 10;
        }
        int number = (Math.abs(n) / modTens) % 10;
        return number;
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long factorial = 1;
        for (int i = 1; i < n + 1; i++) {
            factorial = factorial * i;
        }
        return factorial;
    }
}
