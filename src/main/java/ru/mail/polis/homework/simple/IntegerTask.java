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
        // Считаем арифметическую прогрессию
        if (n < 1) {
            System.out.println("IntegerTask -> sum(): \"n\": " + n + " < 1");
            return 0;
        }

        return ((1 + n) * n) / 2;
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        // Если у гусеницы высота подьёма меньше, чем высота спуска и при этом
        // гусеница не может доползти до потолка за один день, то гусеница никодна не достигнет потолка
        if ((bottom >= top && top < height)) {
            return Integer.MAX_VALUE;
        }

        int counter = 0;
        while (height > 0) {
            height -= top;
            if (height <= 0) {
                return ++counter;
            } else {
                height += bottom;
                counter++;
            }
        }
        return counter;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        if (order < 1) {
            System.out.println("IntegerTask -> kDecimal(): Invalid argument \"order\": " + order);
            return -1;
        }

        int temp = (int) Math.pow(10, order);
        return Math.abs(n % temp / (temp / 10));
    }

    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        if (n < 0) {
            System.out.println("IntegerTask -> factorial(): \"n\": " + n + " < 0");
            return -1;
        }

        long result = 1;
        for (byte i = 2; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
