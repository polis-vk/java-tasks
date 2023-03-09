package ru.mail.polis.homework.simple;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        BigDecimal sumAccumulator = new BigDecimal(0);
        for (double i = a; i <= b; i += delta) {
            sumAccumulator = sumAccumulator.add(BigDecimal.valueOf(function.applyAsDouble(i) * delta));
        }
        return sumAccumulator.doubleValue();
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        String aStr = String.valueOf(a);
        boolean isAPositive = a >= 0;
        int aMax = 0;
        while (a != 0) {
            aMax = (int) Math.max(aMax, Math.abs(a % 10));
            a /= 10;
        }
        if (isAPositive) {
            return (byte) (aStr.indexOf(String.valueOf(aMax)) + 1);
        }
        return (byte) (aStr.indexOf(String.valueOf(aMax)));
    }

    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (x3 - x1) * (y2 - y1) / (x2 - x1) + y1;
        // Не проходит последний тест, не понимаю в чем проблема...  Help pls
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        int[] vectorAC = {x3 - x1, y3 - y1};
        int[] vectorBD = {x4 - x2, y4 - y2};
        double lengthOfAC = Math.sqrt(Math.pow(vectorAC[0], 2) + Math.pow(vectorAC[1], 2));
        double lengthOfBD = Math.sqrt(Math.pow(vectorBD[0], 2) + Math.pow(vectorBD[1], 2));
        double cosAngleACBD = (vectorAC[0] * vectorBD[0] + vectorAC[1] * vectorBD[1]) /
                (Math.abs(lengthOfAC) * Math.abs(lengthOfBD));
        if (Double.isNaN(cosAngleACBD)) {
            return 0;
        }
        double sinAngleACBD = Math.sin(Math.acos(cosAngleACBD));
        return 0.5 * lengthOfAC * lengthOfBD * sinAngleACBD;
    }

}
