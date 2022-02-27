package ru.mail.polis.homework.simple;

import java.lang.Math.*;

public class IntegerTask {

    public static int sum(int n) {
        int result = 0;
        do {
            result += n;
            n -= 1;
        } while (n > 0);
        return result;
    }

    public static int snake(int height, int top, int bottom) {
        if (top <= bottom && height > top) {
            return Integer.MAX_VALUE;
        }
        int result = 0;
        while (true) {
            result += 1;
            height -= top;
            if (height <= 0) {
                return result;
            } else {
                height += bottom;
            }
        }
    }

    public static int kDecimal(int n, int order) {
        for (int i = 1; i < order; i++) {
            n /= 10;
        }
        int result = Math.abs(n) % 10;
        return result;
    }

    public static long factorial(byte n) {
        long result = 1;
        while (n != 0) {
            result *= n;
            n -= 1;
        }
        return result;
    }
}
