package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        //integral = сумме площадей прямоугольников
        double integral = 0;

        for (double i = a; i < b; i += delta) {
            integral += delta * function.applyAsDouble(i);
        }

        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        if (a == 0) {
            return 1;
        }

        int length = (int) (Math.log10(a) + 1);
        long value = a;
        int maxDigit = (int) (value / Math.pow(10, length - 1)); //максимальная цифра
        value = (long) (a % Math.pow(10, length - 1));
        int countMax = 1;  //номер максимальной цифры
        int current = 0;

        for (int i = 2; i <= length; i++) {
            if (maxDigit == 9) {
                return (byte) countMax;
            }

            current = (int) (value / Math.pow(10, length - i));
            if (current > maxDigit) {
                maxDigit = current;
                countMax = i;
            }

            value = (long) (a % Math.pow(10, length - i));
        }

        return (byte) countMax;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        //y=kx+b
        double k = (double) (y1 - y2) / (x1 - x2);
        double b = y1 - k * x1;

        return (k * x3 + b);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        /*
         * S = 0.5 * d1 * d2 * sin;
         * d1, d2 - диагонали
         * sin - угол между диагоналями
         */
        double d1 = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));
        double d2 = Math.sqrt(Math.pow(x2 - x4, 2) + Math.pow(y2 - y4, 2));

        if (d1 == 0 || d2 == 0) {
            return 0;
        }
        //k1, k2 - угловые коэффициенты диагоналей
        double k1 = (double) (y1 - y3) / (x1 - x3);
        double k2 = (double) (y2 - y4) / (x2 - x4);
        double sin = Math.sin(Math.atan(Math.abs((k2 - k1) / (1 + k1 * k2))));

        if ((x1 - x3) == 0 || (x2 - x4) == 0) {
            sin = 1;
        }

        return (d1 * d2 * sin) / 2;
    }
}
