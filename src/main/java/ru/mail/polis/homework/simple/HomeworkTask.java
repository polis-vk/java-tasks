package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double result = 0;
        while (b > a) {
            result += function.applyAsDouble(a) * delta;
            a += delta;
        }
        return result;
    }

    public static byte maxNumber(long a) {
        a = Math.abs(a);
        byte result = 0;
        byte iteration = 1;
        byte buff = 0;
        do {
            if (a % 10 >= buff) {
                buff = (byte) (a % 10);
                result = iteration;
            }
            iteration += 1;
            a /= 10;
        } while (a > 0);
        result = (byte) (iteration - result);
        return result;
    }

    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double prom = ((x3 - x1) * (y2 - y1));
        double result = prom / (x2 - x1) + y1;
        return result;
    }

    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double result = 0.5d * Math.abs((x1 * y2 + x2 * y3 + x3 * y4 + x4 * y1 - x2 * y1 - x3 * y2 - x4 * y3 - x1 * y4));
        return result;
    }

}
