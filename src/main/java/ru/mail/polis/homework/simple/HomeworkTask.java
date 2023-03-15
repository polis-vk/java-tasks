package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double x = a;
        double integral = 0;
        while (x <= b) {
            integral += function.applyAsDouble(x) * delta;
            x += delta;
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long aHelp = Math.abs(a);
        long factorWholePart = (long) Math.pow(10, (int)Math.log10(aHelp));
        long currentFig;
        long max = 0;
        long counter = 1;
        long numMax = 1;
        while (factorWholePart != 0) {
            currentFig = aHelp / factorWholePart;
            currentFig %= 10;
            if (currentFig > max) {
                max = currentFig;
                numMax = counter;
                if (max == 9) {
                    break;
                }
            }
            factorWholePart /= 10;
            counter++;
        }
        return (byte) numMax;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) { //Воспользовался каноническим уравнением прямой на плоскости и выразил y
        return (double) (y2 - y1) / (x2 - x1) * (x3 - x1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double diagonalAC = Math.sqrt((y1 - y3) * (y1 - y3) + (x3 - x1) * (x3 - x1));
        double diagonalBD = Math.sqrt((y2 - y4) * (y2 - y4) + (x4 - x2) * (x4 - x2));
        double squareS = 0;
        if (diagonalAC > 0 && diagonalBD > 0) {
            double parM1 = x3 - x1;
            double parN1 = y3 - y1;
            double parM2 = x4 - x2;
            double parN2 = y4 - y2;
            double cosCornerNum = Math.abs(parM1 * parM2 + parN1 * parN2);
            double cosCornerDen = Math.sqrt(parM1 * parM1 + parN1 * parN1) * Math.sqrt(parM2 * parM2 + parN2 * parN2);
            double sinCorner = Math.sin(Math.acos(cosCornerNum / cosCornerDen));
            squareS = 0.5 * diagonalAC * diagonalBD * sinCorner;
        }
        return squareS;
    }
}
