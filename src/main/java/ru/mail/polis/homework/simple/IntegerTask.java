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
        return ((1 + n) * n) / 2;  // формула суммы арифметической прогрессии
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

        /* (height - top) означает, что мы не учитываем расстояние, когда гусенице осталось добраться до
         *  потолка за один прыжок вверх; (top - bottom) означает шаг, с которым гусеница доберется до такой точки
         *  откуда она сможет гарантированно добраться до потолка за один день (это слагаемое "+1")*/
        return (int) (Math.ceil((double) (height - top) / (top - bottom)) + 1);
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int num = (int) (n / Math.pow(10, order - 1));
        return Math.abs(num % 10);
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long product = 1;
        for (int i = 1; i <= n; i++) {
            product *= i;
        }

        return product;
    }
}
