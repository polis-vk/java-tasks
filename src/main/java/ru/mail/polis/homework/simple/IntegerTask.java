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
        int result = 0;
        for (int i = 1; i<=n; i++){
            result += i;
        }
        return result;
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        int totalHeight = 0;
        int days = 0;
        if (top >= height) {
            return  1;
        }
        if (bottom >= top) {
            return Integer.MAX_VALUE;
        }
        while (totalHeight < height) {
            totalHeight += top;
            days++;
            if (totalHeight != height) {
                totalHeight -= bottom;
            }
        }
        return days;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int result = 0;
        for (int i = 1; i < order; i++) {
            n /= 10;
        }
        result = Math.abs(n%10);
        return result;
    }

    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long result = 1;
        for(int i = 2;i <= n;i++) {
            result*=i;
        }
        return result;
    }
}
