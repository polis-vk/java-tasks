package ru.mail.polis.homework.simple;


import javax.swing.plaf.IconUIResource;

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
        while (n != 0) {
            sum += n;
            n--;
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
        int day_counter = 0;
        if (bottom >= height) {
            return 1;
        }

        if (bottom >= top) {
            return Integer.MAX_VALUE;
        } else {
            for (int current_hight = bottom; current_hight < height; current_hight += (top - bottom)) {
                day_counter++;
            }
        }

        return day_counter;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int value = Math.abs(n) % (int) (Math.pow(10, order));
        value /= (int) (Math.pow(10, order - 1));
        return value;
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long value = 1;
        while (n >= 1) {
            value *= n;
            n--;
        }
        return value;
    }
}
