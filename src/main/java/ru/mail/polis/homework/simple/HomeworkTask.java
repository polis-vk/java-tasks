package ru.mail.polis.homework.simple;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    // В решении используется составная квадратурная формула трапеций
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double sumFunctions = 0;
        double xCurrent = a;
        int nIntervals = (int) ((b - a) / delta);

        for (int k = 1; k < nIntervals; ++k) {
            sumFunctions += function.applyAsDouble(xCurrent);
            xCurrent += delta;
        }

        double fByA = function.applyAsDouble(a);
        double fByB = function.applyAsDouble(b);

        return (b - a) / (2 * nIntervals) * (fByA + 2 * sumFunctions + fByB);
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        String numString = Long.toString(a);

        if (a < 0) {
            numString = numString.substring(1);
        }

        CharacterIterator it = new StringCharacterIterator(numString);

        char maxNum = it.current();
        it.next();

        int indexCurrent = 1;
        while (it.current() != CharacterIterator.DONE) {
            if (maxNum < it.current()) {
                maxNum = it.current();
                indexCurrent = it.getIndex() + 1;
            }
            it.next();
        }

        return (byte) indexCurrent;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double diffX = x2 - x1;
        double diffY = y2 - y1;
        double k = (diffX == 0) ? 0 : (diffY / diffX);
        double b = y1 - k * x1;

        return k * x3 + b;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    // В решении вычисляется сумма площадей двух треугольников по формуле Герона, на которые поделен четырехугольник
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        int[] xArray = new int[]{x1, x2, x3, x4};
        int[] yArray = new int[]{y1, y2, y3, y4};

        bubbleSort(xArray, yArray);
        if ((yArray[1] < yArray[0]) & (yArray[1] < yArray[2])) {
            swapInArray(xArray, yArray, 0, 1);
        }
        if ((yArray[2] < yArray[3]) & (yArray[1] > yArray[2])) {
            swapInArray(xArray, yArray, 2, 3);
        }

        double a = distanceBetweenTwoPoints(xArray[0], yArray[0], xArray[2], yArray[2]);
        double b;
        double c;
        double square = 0;

        for (int i = 1; i < xArray.length; i += 2) {
            b = distanceBetweenTwoPoints(xArray[i], yArray[i], xArray[0], yArray[0]);
            c = distanceBetweenTwoPoints(xArray[i], yArray[i], xArray[2], yArray[2]);
            square += heronsFormula(a, b, c);
        }

        return square;
    }

    public static void bubbleSort(int[] xArray, int[] yArray) {
        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < xArray.length - 1; ++i) {
                if (xArray[i] > xArray[i + 1]) {
                    swapInArray(xArray, yArray, i, i + 1);
                    isSorted = false;
                }
            }
        }
    }

    public static void swapInArray(int[] xArray, int[] yArray, int i1, int i2) {
        if ((i1 >= xArray.length) || (i2 >= yArray.length)) {
            return;
        }
        int xTemp = xArray[i1];
        int yTemp = yArray[i1];
        xArray[i1] = xArray[i2];
        yArray[i1] = yArray[i2];
        xArray[i2] = xTemp;
        yArray[i2] = yTemp;
    }

    public static double distanceBetweenTwoPoints(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
    }

    public static double heronsFormula(double a, double b, double c) {
        double p = (a + b + c) / 2.0;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

}
