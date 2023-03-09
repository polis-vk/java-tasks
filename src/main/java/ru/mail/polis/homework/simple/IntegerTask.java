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
        int count = 0;
        for (int i = 1; i <= n; i++) {
            count += i;
        }
        return count;
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
        if (top <= bottom) {
            return Integer.MAX_VALUE;
        }
        /*(height - top) - расстояние, которое нужно проползти гусенице без последнего поднятия.
        (top - bottom) - расстояние, которое проползает гусеница за сутки.
        Частное этих значений равно количеству дней без последнего поднятия. После прибавления единицы получаем
        количество дней, чтобы подняться до height*/
        return (int) (Math.ceil((double) (height - top) / (top - bottom)) + 1);
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int number = (int) (n / (Math.pow(10, order - 1)) % 10);
        if (n < 0) {
            return Math.abs(number);
        }
        return number;
    }

    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        if (n <= 1) {
            return 1;
        }
        return n * factorial((byte) (n - 1));
    }
}
