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
        int sum = 0;
        for (int i = 1; n >= i; i++) {
            sum += i;
        }
        return sum;
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        if (bottom >= top && top < height) {
            return Integer.MAX_VALUE;
        }
        int counter = 0;
        int heightSnake = 0;
        boolean chekTime = true;
        while (heightSnake < height) {
            if (chekTime) {//день
                heightSnake += top;
                if (heightSnake >= height) {
                    counter++;
                }
            } else {//ночь
                heightSnake -= bottom;
                counter++;
            }
            chekTime = !chekTime;
        }

        return counter;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        int num = n;
        int count = 0;
        while (num != 0) {
            if (count == order) {
                return Math.abs(num % 10);
            }
            if (count + 1 != order) {
                num /= 10;
            }
            count++;
        }
        return Integer.MAX_VALUE;//тут оно просило что-то вернуть, решил вернуть Integer.MAX_VALUE, не хочу
        // -1 тубрика не знал что вернуть, в тестах нет ситуации, когда у числа разрядов меньше
        // чем запрашиваемый выводимый разряд
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long factorial = 1;
        for (byte i = 2; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }
}
