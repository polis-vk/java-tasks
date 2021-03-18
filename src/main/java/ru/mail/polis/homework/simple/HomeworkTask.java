package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double res = 0;

        for (double x = a; x <= b; x += delta) {
            res += delta * function.applyAsDouble(x);
        }

        return res;
    }

    public static final byte MAX_NUMERAL = 9;

    public static byte maxNumber(long a) {
        byte indexOfMaxNumeral = 0;
        byte maxNumeral = 0;
        char[] numberString = Long.toString(a).toCharArray();
        for (byte i = 0; i < numberString.length; i++) {
            byte nowNumeral = Byte.parseByte(String.valueOf(numberString[i]));
            if (nowNumeral > maxNumeral) {
                maxNumeral = nowNumeral;
                indexOfMaxNumeral = i;
                if (maxNumeral == MAX_NUMERAL) {
                    return (byte) (indexOfMaxNumeral + 1);
                }
            }
        }
        return (byte) (indexOfMaxNumeral + 1);
    }

    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double equationForX3 = ((double) x3 - (double) x1) / ((double) x2 - (double) x1);
        return equationForX3 * ((double) y2 - (double) y1) + (double) y1;
    }

    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double firstTerm = x1 * y2 - y1 * x2;
        double secondTerm = x2 * y3 - y2 * x3;
        double thirdTerm = x3 * y4 - y3 * x4;
        double forthTerm = x4 * y1 - y4 * x1;

        return Math.abs(firstTerm + secondTerm + thirdTerm + forthTerm) / 2;
    }

}
