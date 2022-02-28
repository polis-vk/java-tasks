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
        int amount = 0;
        for (int i = 0; i <= n; ++i){
            amount += i;
        }
        return amount;
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        int day_s = 0, way = 0;
        while (way < height){
            ++day_s;
            way += top;
            if (way >= height){
                continue;
            }
            if (top <= bottom){
                return Integer.MAX_VALUE;
            }
            way -= bottom;
        }
        return day_s;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        if(n < 0){
            n *= -1;
        }
        int ten_extent = 1;
        for (int i = 1; i < order; ++i){
            ten_extent *= 10;
        }
        return Math.abs((n/ten_extent)%10);
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long factorial_result = 1;
        for (int i = 1; i <= n; ++i) {
            factorial_result *= i;
        }
        return factorial_result;
    }
}
