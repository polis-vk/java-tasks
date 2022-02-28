package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double result = 0;
        double step = a;
        while (b > step) {
            result += function.applyAsDouble(step) * delta;
            step += delta;
        }
        return result;
    }

    public static byte maxNumber(long a) {
        long number;
        if (a == Long.MIN_VALUE) {
            number = a - 1;
        } else {
            number = Math.abs(a);
        }
        int result = 0;
        int iteration = 1;
        long buff = 0;
        do {
            if (number % 10 >= buff) {
                buff = number % 10;
                result = iteration;
            }
            iteration += 1;
            number /= 10;
        } while (number > 0);
        return (byte) (iteration - result);
    }

    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double temp = ((x3 - x1) * (y2 - y1));
        return temp / (x2 - x1) + y1;
    }

    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return 0.5d * Math.abs((x1 * y2 + x2 * y3 + x3 * y4 + x4 * y1 - x2 * y1 - x3 * y2 - x4 * y3 - x1 * y4));
    }

}
