package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double calcIntegral = 0;
        for (double step = a; step <= b; step += delta) {
            calcIntegral = calcIntegral + delta * function.applyAsDouble(step);
        }
        return calcIntegral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte numberOfIndexes = 0;
        long comparator = Math.abs(a);
        long maxDigitOfNumber;
        byte maxNumber = 0;

        if (a == 0) {
            return 1;
        }

        if (a == Long.MIN_VALUE) {
            return 1;
        }

        while (comparator >= 1) {
            numberOfIndexes++;
            comparator = comparator / 10;
        }

        System.out.println(comparator);
        System.out.println(numberOfIndexes);
        comparator = Long.MIN_VALUE;

        for (int i = 1; i <= numberOfIndexes; i++) {
            maxDigitOfNumber = ((Math.abs(a) / ((long) Math.pow(10, numberOfIndexes - i))) % 10);
            if (maxDigitOfNumber > comparator) {
                comparator = maxDigitOfNumber;
                maxNumber = (byte) i;
            }
        }
        return maxNumber;
    }

    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double y3 = (double) (-x1 * y2 + x2 * y1 - (y1 - y2) * x3) / (x2 - x1);
        return y3;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        if (((x1 == x2) & (y1 == y2)) || ((x1 == x3) & (y1 == y3)) || ((x1 == x4) & (y1 == y4)) ||
                ((x2 == x3) & (y2 == y3)) || ((x2 == x4) & (y2 == y4)) || ((x3 == x4) & (y3 == y4))) {
            return 0;
        }
        // узнаем диагонали четырехугольника
        double lengthAC = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));
        double lengthBD = Math.sqrt((x2 - x4) * (x2 - x4) + (y2 - y4) * (y2 - y4));
        // узнаем угол между диагоналями
        double angle = ((x1 - x3) * (x2 - x4) + (y1 - y3) * (y2 - y4)) /
                Math.sqrt(((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3)) *
                        ((x2 - x4) * (x2 - x4) + (y2 - y4) * (y2 - y4)));
        // преобразуем в синус
        angle = Math.sqrt(1 - angle * angle);
        // считаем площадь
        double square = (lengthBD * lengthAC * angle) * 0.5;
        return square;
    }

}
