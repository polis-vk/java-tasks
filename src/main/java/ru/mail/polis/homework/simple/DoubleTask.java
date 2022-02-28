package ru.mail.polis.homework.simple;

public class DoubleTask {

    public static String equation(int a, int b, int c) {
        double D = b * b - 4 * a * c;
        double x1 = (-b - Math.sqrt(D)) / (2 * a);
        double x2 = (-b + Math.sqrt(D)) / (2 * a);
        if (x1 < x2) {
            return x2 + ", " + x1;
        }
        return x1 + ", " + x2;
    }

    public static float length(double x1, double y1, double x2, double y2) {
        return (float) (Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1)));
    }
}
