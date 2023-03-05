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
        int currentHeight = 0;
        int days = 0;
        if (bottom >= top && top < height) {
            return Integer.MAX_VALUE;
        }
        while (currentHeight < height) {
            days++;
            currentHeight += top;
            if (currentHeight < height) {
                currentHeight -= bottom;
            }
        }
        return days;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int b = (int) ((n % Math.pow(10, order)) / Math.pow(10, order - 1));
        return Math.abs(b);
    }

    public static int kDecimal(long n, int order) { // добавил перегрузку для того, чтобы использовать метод в HomeworkTask, не знаю, можно ли изменять сигнатуру методов в дз.
        int b = (int) ((n % Math.pow(10, order)) / Math.pow(10, order - 1));
        return Math.abs(b);
    }

    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        return n == 0 ? 1 : n * factorial((byte) (n - 1));
    }
}
