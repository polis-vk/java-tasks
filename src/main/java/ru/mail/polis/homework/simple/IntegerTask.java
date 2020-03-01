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
        if (n>0){
            int amount = 0;
            while(n>0){
                amount+=n;
                n-=1;
            }
            return amount;
        }else{
            System.out.println("Error...Incorrect input");
            return 0;
        }
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        if (top>height){
            return 1;
        }else if(top>bottom){
            return (height-top)/(top-bottom)+1;
        }else{
            return Integer.MAX_VALUE;
        }
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стояющую на нужном разряде
     * Пример: (454355, 3) -> 3
     */
    public static int kDecimal(int n, int order) {
        n = (int) Math.abs((n / Math.pow(10, (order - 1)))%10);
        return n;
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        if (n>0) {
            long fac = 1;
            while (n != 0) {
                fac *= n;
                n -= 1;
            }
            return fac;
        }else if(n==0){
            return 1;
        }else{
            System.out.println("Error...Incorrect input");
            return 0;
        }
    }
}
