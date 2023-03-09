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
        for (double i = a; i < b; i += delta) {
            integral += function.applyAsDouble(i) * delta;
        }

        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long maxDigit = 0;
        long digit = 0;
        int serialNumber = 0;
        int maxSerialNumber = 0;
        long copy = a;
        while (copy != 0) {
            serialNumber++;
            digit = Math.abs(copy) % 10;
            if (digit >= maxDigit) {
                maxSerialNumber = serialNumber;
                maxDigit = digit;
            }
            copy /= 10;
        }
        return (byte) (serialNumber - maxSerialNumber + 1);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double k = (y2 - y1) / (double) (x2 - x1);
        return k * (x3 - x1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double ab = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        double bc = Math.sqrt(Math.pow((x3 - x2), 2) + Math.pow((y3 - y2), 2));
        double ac = Math.sqrt(Math.pow((x3 - x1), 2) + Math.pow((y3 - y1), 2));
        double semiPerimeter1 = (ab + bc + ac) / 2;
        double area1 = Math.sqrt(semiPerimeter1 * (semiPerimeter1 - ab) * (semiPerimeter1 - bc) * (semiPerimeter1 - ac));
        double cd = Math.sqrt(Math.pow((x4 - x3), 2) + Math.pow((y4 - y3), 2));
        double da = Math.sqrt(Math.pow((x1 - x4), 2) + Math.pow((y1 - y4), 2));
        double semiPerimeter2 = (cd + da + ac) / 2;
        double area2 = Math.sqrt(semiPerimeter2 * (semiPerimeter2 - cd) * (semiPerimeter2 - da) * (semiPerimeter2 - ac));
        return area1 + area2;
    }
}
