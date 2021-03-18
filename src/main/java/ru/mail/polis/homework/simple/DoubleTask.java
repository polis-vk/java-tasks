package ru.mail.polis.homework.simple;

public class DoubleTask {

    public static String equation(int a, int b, int c) {
        double x1;
        double x2;

        if (a == 0) {
            x1 = -(double) c / (double) b;
            x2 = x1;
            return x1 + ", " + x2;
        }

        double discriminant = Math.pow(b, 2) - 4 * (double) a * (double) c;
        x1 = (-(double) b + Math.sqrt(discriminant)) / (2 * (double) a);
        x2 = (-(double) b - Math.sqrt(discriminant)) / (2 * (double) a);

        return Math.max(x1, x2) + ", " + Math.min(x1, x2);
    }

    public static float length(double x1, double y1, double x2, double y2) {
        double differenceX = Math.abs(x2 - x1);
        double differenceY = Math.abs(y2 - y1);

        return (float) Math.sqrt(Math.pow(differenceX, 2) + Math.pow(differenceY, 2));
    }

}
