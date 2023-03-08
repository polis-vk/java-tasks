package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
//        function.applyAsDouble(3)
        return 0;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long factorWholePart = 1;
        long aHelp = a;
        aHelp = Math.abs(aHelp);
        while (aHelp / 10 / factorWholePart != 0) {
            factorWholePart *= 10;
        }
        long currentFig;
        long max = 0;
        long factorRemainder = 10;
        long counter = 1;
        long numMax = -1;
        while (factorWholePart != 0) {
            currentFig = aHelp / factorWholePart;
            currentFig %= factorRemainder;
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
        if (numMax == -1) {
            numMax = 1;
        }
        return (byte) numMax;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double xDen = x2 - x1, yDen = y2 - y1;
        double y3 = yDen / xDen * (x3 - x1) + y1; //Воспользовался каноническим уравнением прямой на плоскости и выразил y
        return y3;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double parM1, parN1;
        double parM2, parN2;
        double cosCornerNum;
        double cosCornerDen;
        double sinCorner;
        double squareS = 0;
        double diagonalAC = Math.sqrt((y1 - y3) * (y1 - y3) + (x3 - x1) * (x3 - x1));
        double diagonalBD = Math.sqrt((y2 - y4) * (y2 - y4) + (x4 - x2) * (x4 - x2));
        if (diagonalAC > 0 && diagonalBD > 0) {
            parM1 = x3 - x1;
            parN1 = y3 - y1;
            parM2 = x4 - x2;
            parN2 = y4 - y2;
            cosCornerNum = Math.abs(parM1 * parM2 + parN1 * parN2);
            cosCornerDen = Math.sqrt(parM1 * parM1 + parN1 * parN1) * Math.sqrt(parM2 * parM2 + parN2 * parN2);
            sinCorner = Math.sin(Math.acos(cosCornerNum / cosCornerDen));
            squareS = 0.5 * diagonalAC * diagonalBD * sinCorner;
        }
        return squareS;
    }
}
