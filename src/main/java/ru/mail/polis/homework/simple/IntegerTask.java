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
        //Формула арифметической прогрессии
        int sumOfNumbers = (1 + n) * n / 2;
        return sumOfNumbers;
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        if (top >= height) { //Если может добраться за день, спуск можно не учитывать
            return 1;
        }
        else if (top <= bottom) { //Если за день не добраться и значение спуска больше значения подъема, то будет только спускаться
            return Integer.MAX_VALUE;
        }
        else if ((height - top) % (top - bottom) != 0) {
            return (height - top) / (top - bottom) + 2;
        }
        else {
            return (height - top) / (top - bottom) + 1;
        }
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int newOrder = 1; //Порядок очередной цифры числа
        int figure = 0; //Очередная цифра числа с копированием знака числа, например в числе -111 каждая очередная цифра равна -1
        while (n != 0) {
            figure = n % 10;
            if (newOrder == order) {
                return Math.abs(figure);
            }
            newOrder++;
            n /= 10;
        }
        return Math.abs(figure);
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long valueOfFactorial = 1;
        for (int i = 1; i <= n; i++) {
            valueOfFactorial *= i;
        }
        return valueOfFactorial;
    }
}
