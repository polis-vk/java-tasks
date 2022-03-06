package ru.mail.polis.homework.simple;

import ru.mail.polis.homework.io.blocking.StructureOutputStream;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integ = 0;
        for (double i = a; i <= b; i += delta) {
            double fun = function.applyAsDouble(i);
            integ += fun * delta;
        }
        return integ;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        // Находим длину числа
        long b = a;
        byte len = 0;
        if (b == 0) {
            len = 1;
        }
        if (a == Long.MIN_VALUE) {
            return 1;
        }
        while (b != 0) {
            b /= 10;
            len++;
        }

        // Находим максималную цифру числа

        double maxNum = Math.abs(a) / Math.pow(10, len - 1);
        byte max = (byte) maxNum;
        byte indexOfMax = 1;
        byte indexOfNum = 1;

        for (int i = len - 2; i >= 0; i--) {
            indexOfNum++;
            double num = Math.abs(a) % Math.pow(10, i + 1) / Math.pow(10, i);
            byte maybeItIsMax = (byte) num;
            if (maybeItIsMax > max) {
                max = maybeItIsMax;
                indexOfMax = indexOfNum;

            }
        }

        return indexOfMax;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return ((double)x3 - x1) / (x2 - x1) * (y2 - y1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        // Найдем диагонали
        double d1 = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
        double d2 = Math.sqrt(Math.pow(x4 - x2, 2) + Math.pow(y4 - y2, 2));

        // ССдучай, если точки совпадают
        if (d1 == 0 || d2 == 0) {
            return 0;
        }

        // Найдем косинус
        double cosBetweenDiag = Math.abs(((x3 - x1) * (x4 - x2) + (y3 - y1) * (y4 - y2))) / (Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2)) * Math.sqrt(Math.pow(x4 - x2, 2) + Math.pow(y4 - y2, 2)));

        return d1 * d2 * Math.sqrt(1 - Math.pow(cosBetweenDiag, 2)) / 2;
    }

}
