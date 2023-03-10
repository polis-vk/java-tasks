package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integral = 0;
        for (double x = a; x <= b; x += delta) {
            integral += function.applyAsDouble(x) * delta;
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte nDigits = (byte) ((int) Math.log10(Math.abs(a)) + 1);
        byte maxDigit = 0;
        byte index = 1;
        for (byte i = 1; i <= nDigits; i++) {
            byte currentDigit = (byte) Math.abs(a / (Math.pow(10, nDigits - i)) % 10);
            if (currentDigit == 9) {
                return i;
            }
            if (currentDigit > maxDigit) {
                maxDigit = currentDigit;
                index = i;
            }
        }
        return index;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (double) (x3 - x1) / (x2 - x1) * (y2 - y1) + y1; // Canonical equation of straight line
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double[] vectorAC = {x3 - x1, y3 - y1};
        double[] vectorBD = {x4 - x2, y4 - y2};
        double modAC = Math.sqrt(Math.pow(vectorAC[0], 2) + Math.pow(vectorAC[1], 2));
        double modBD = Math.sqrt(Math.pow(vectorBD[0], 2) + Math.pow(vectorBD[1], 2));
        if (modAC * modBD == 0) {
            return 0;
        }
        double sinDOC = Math.abs(vectorAC[0] * vectorBD[1] - vectorAC[1] * vectorBD[0]) / (modAC * modBD);
        return 0.5 * modAC * modBD * sinDOC;
    }
}
