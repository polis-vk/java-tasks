package ru.mail.polis.homework.simple;

import java.lang.Math.*;

public class IntegerTask {

    public static int sum(int n) {
        int result = 0;
        int number = n;
        do {
            result += number;
            number -= 1;
        } while (number > 0);
        return result;
    }

    public static int snake(int height, int top, int bottom) {
        if (top <= bottom && height > top) {
            return Integer.MAX_VALUE;
        }
        int peak = height;
        int result = 0;
        while (true) {
            result += 1;
            peak -= top;
            if (peak <= 0) {
                return result;
            } else {
                peak += bottom;
            }
        }
    }

    public static int kDecimal(int n, int order) {
        int number = n;
        for (int i = 1; i < order; i++) {
            number /= 10;
        }
        return Math.abs(number) % 10;
    }

    public static long factorial(byte n) {
        long result = 1;
        byte number = n;
        while (number != 0) {
            result *= number;
            number -= 1;
        }
        return result;
    }
}
