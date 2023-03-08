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
        return (int) ((n / 2.0) * (2 + n - 1));
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        if ((bottom >= top && top < height) || height == 0) {
            return Integer.MAX_VALUE;
        }
        double days = (height - top) > 0 ? (height - top) / ((double) (top - bottom)) + 1 : 1;
        return (int) Math.ceil(days);
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int tempValue = (int) ((n % Math.pow(10, order)) / Math.pow(10, order - 1));
        return Math.abs(tempValue);
    }

    public static int kDecimal(long n, int order) { // добавил перегрузку для того, чтобы использовать метод в HomeworkTask, не знаю, можно ли изменять сигнатуру методов в дз.
        int numDigit = (int) ((n % Math.pow(10, order)) / Math.pow(10, order - 1));
        return Math.abs(numDigit);
    }

    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        return n == 0 ? 1 : n * factorial((byte) (n - 1));
    }
}
