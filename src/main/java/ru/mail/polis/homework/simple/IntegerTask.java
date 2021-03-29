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
        for (int i = 1; i <= n; i++) {
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
        int kdays = 0;/**количество дней**/
        int summ = 0;

        if (!((top > bottom) || (top >= height))) {
            return Integer.MAX_VALUE;
        }

        while (summ <= height) {
            summ += top;
            kdays++;
            if (summ >= height) {
                break;
            }
            summ -= bottom;
        }
        return kdays;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int nCopy = n;
        if (nCopy < 0) {
            nCopy *= -1;
        }
        int answ = 0;
        int miniNumber = nCopy / (int) Math.pow(10, (order - 1));
        answ = miniNumber % 10;
        return answ;
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long fact = 1;
        for (long i = 2; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
}
