package ru.mail.polis.homework.simple;
import java.lang.Math.*;


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
        int res = 0;
        for (int i = 1; i <= n; i++) {
            res += i;
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
        int res = 0;
        int i = 0;
        while (res < height){
            i++;
            res += top;
            if (res >= height) return i;
            res -= bottom;
            if (res <= 0) return Integer.MAX_VALUE;
        }
        return i;
    }

        /**
         * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
         * Пример: (454355, 3) -> 3
         */

        public static int kDecimal(int n, int order) {
            double mod = Math.pow(10, order);
            double div = Math.pow(10, order - 1);
            return Math.abs((int) ((int) (n % mod) / div));
        }



        /**
         * Выведите факториал от числа n
         * Пример: (5) -> 120
         */
        public static long factorial( byte n){
            long res = 1;
            for (int i = 1; i <= n; i++ ) {
                res *= i;
            }
            return res;
        }


}
