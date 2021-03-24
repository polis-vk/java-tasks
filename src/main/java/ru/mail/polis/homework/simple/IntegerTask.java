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
        int f = 1;
        int res = 0;
        int newN = n + 1;
        while (f > 0) {
            f = newN - 1;
            res += f;
            newN = f;
        }
        return res;
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        int dif = top - bottom;
        if (top >= height) return 1;
        if (top < 0 && bottom < 0 && dif <= 0) {
            return Integer.MAX_VALUE;
        }
        int days = 0;
        int length = 0;
        if (dif <= 0) {
            return Integer.MAX_VALUE;
        }
        while (length < height) {
            length += dif;
            days++;
        }
        return days - bottom;
    }


        /*int count = 0;
        int result = height;
        if (bottom > top) return Integer.MAX_VALUE;
        else {
            while (result != 0) {
                result -= top - bottom;
                count++;
            }
        }
        return count;
        */

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int count = 0;
        int number = 0;
        int newNumber = n;
        while (count < order && newNumber != 0) {
            number = newNumber % 10;
            newNumber = newNumber / 10;
            count += 1;
        }
        return Math.abs(number);
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long nextNumber = n;
        long n1 = n;
        if (n <= 1) {
            return 1;
        }
        while (n1 > 1) {
            nextNumber = ((n1 - 1) * nextNumber);
            n1 = (n1 - 1);
        }
        return nextNumber;
    }
}
