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
        if(n < 1){
            return 0;
        }

        int sum = 0;
        for(int i = 1; i <= n; i++){
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
        if(height <= 0 || top <= 0 || bottom <= 0){
            throw new IllegalArgumentException("Incorrect values");
        }

        if(top >= height){
            return 1;
        }

        int shift = top - bottom;
        if(shift <= 0){
            return Integer.MAX_VALUE;
        }
        byte extraDay = 0;
        if((height - top)%shift != 0){
            extraDay = 1;
        }
        return (height - top)/shift + 1 + extraDay;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        if(order <= 0){
            throw new IllegalArgumentException("Invalid oreder");
        }
        int orderGetter = (int) Math.pow(10, order - 1);
        if(Math.abs(n) < orderGetter){
            throw new IllegalArgumentException("There is no such order in the number provided");
        }

        return Math.abs((n / orderGetter) % 10);
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        if(n < 0){
            throw new IllegalArgumentException("Can't get a factorial of a negative number");
        }

        long factorial = 1;
        for(int i = 1; i <= n; i++){
            factorial *= i;
        }

        return factorial;
    }
}
