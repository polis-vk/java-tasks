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
        int summa = 0;

        for (int i = 1; i < n + 1; i++)
        {
            summa += i;
        }
        return summa;
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        int coordinate = top;
        int days = 1;

        if (top <= bottom && top < height)
        {
            days = Integer.MAX_VALUE;
        } else {
            while (coordinate < height)
            {
                coordinate -= bottom;
                coordinate += top;
                days++;
            }
        }
        return days;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int remainder = 0, wholePart = 0;
        int counter = 1;
        int factor = 10;

        while (counter < order){
            factor *= 10;
            counter ++;
        }

        if (order > 1){
            remainder = n % factor;
            wholePart = remainder/(factor/10);
        } else{
            remainder = n % factor;
            wholePart = remainder;
        }
        wholePart = Math.abs(wholePart);
        return wholePart;
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long fact = 1;
        long i = 1;

        while (i < n)
        {
            fact = fact * (i + 1);
            i++;
        }
        return fact;
    }
}
