package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double summ = 0;
        double t = a;/**t - параметр функции**/
        while (t <= b) {
            summ += function.applyAsDouble(t) * delta;
            t += delta;
        }
        return summ;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long lengthNumber = (String.valueOf(a)).length();
        long aCopy = a;
        long Number1 = aCopy % 10;
        long Number2 = 0;
        long number = lengthNumber;
        long max = Number1;
        aCopy = aCopy / 10;
        lengthNumber--;
        for (long i = lengthNumber; i > 0; i--) {
            Number2 = aCopy % 10;
            if (Number2 >= max) {
                number = i;
                max = Number2;
            }
            aCopy = aCopy / 10;
        }
        return (byte) number;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой  что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double y3;
        y3 = (x3 - x1) * (y2 - y1) / (double)(x2 - x1) + y1;
        return y3;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return 0;
    }

}
