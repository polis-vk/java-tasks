package ru.mail.polis.homework.simple;


public class IntegerTask {

    public static int sum(int n) {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    public static int snake(int height, int top, int bottom) {
        int days = 0;
        int nowHeight = 0;
        while (nowHeight < height) {
            days++;
            nowHeight += top;
            if (nowHeight >= height) {
                return days;
            }
            nowHeight -= bottom;
            if (nowHeight <= 0) {
                return Integer.MAX_VALUE;
            }
        }
        return days;
    }

    public static int kDecimal(int n, int order) {
        int forRemainder = (int) Math.pow(10, order);
        int forExcretion = (int) Math.pow(10, order - 1);
        return Math.abs(n) % forRemainder / forExcretion;
    }


    public static long factorial(byte n) {
        long factorial = 1;
        for (int i = 1; i <= n; i++) {
            factorial *= i;
        }
        return factorial;
    }

}
