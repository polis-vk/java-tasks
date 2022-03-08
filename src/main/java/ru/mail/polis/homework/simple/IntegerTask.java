package ru.mail.polis.homework.simple;

import java.lang.Math.*;

public class IntegerTask {

    public static int sum(int n) {
        int result = 0;
        for (int number = 1; number <= n; number++) {
            result += number;
        }
        return result;
    }

    public static int snake(int height, int top, int bottom) {
        if (height <= top) {
            return 1;
        }
        if (top <= bottom) {
            return Integer.MAX_VALUE;
        }
        int peak = height;
        int result = 0;
        while (true) {
            result += 1;
            peak -= top;
            if (peak <= 0) {
                return result;
            }
            peak += bottom;
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
        for (byte i = 1; i <= n; i++) {
            result *= i;
        }
        return result;
    }
}
