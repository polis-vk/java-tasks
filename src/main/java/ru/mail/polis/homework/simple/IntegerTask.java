package ru.mail.polis.homework.simple;


import java.util.stream.IntStream;
import java.util.stream.LongStream;

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
        return IntStream
                .rangeClosed(1, n)
                .sum();
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        int daysResult;
        if (top >= height) {
            daysResult = 1;
        } else {
            int distancePerDay = top - bottom;
            if (distancePerDay <= 0) {
                daysResult = Integer.MAX_VALUE;
            } else {
                double heightWithoutLastMove = height - top;
                daysResult = (int) Math.ceil(heightWithoutLastMove / distancePerDay) + 1;
            }
        }
        return daysResult;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int dividerForLastDigit = (int) Math.pow(10, order - 1);
        return (Math.abs(n) / dividerForLastDigit) % 10;
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        return LongStream
                .rangeClosed(1, n)
                .reduce((x, y) -> x * y)
                .orElse(1); //for n = 0
    }
}
