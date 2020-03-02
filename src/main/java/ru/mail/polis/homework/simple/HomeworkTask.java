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
        for (int i = 0; i < (b - a) / delta; i++) {
            integral += function.applyAsDouble((a + (i * delta) + (delta / 2)));
        }
        return integral * delta;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        int length = (int) Math.ceil(Math.log10(a + 0.01));
        int max = (int) (a / Math.pow(10, length - 1));
        byte index = 1;
        if (max != 9) {
            for (int i = 2; i <= length; i++) {
                int numb = (int) (a / Math.pow(10, length - i)) % 10;
                if (numb > max) {
                    max = numb;
                    index = (byte) i;
                }
            }
        } else {
            return index;
        }
        return index;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double x = x2 - x1; //Если x == 0, то в случае x3 == x2 == x1 подходит любой y3, иначе точки не существует
        double y = y2 - y1;
        if (y == 0) {
            return y1;
        } else {
            double y3 = y2 - ((y * (x2 - x3)) / x);
            return y3;
        }
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double squareResult = Math.abs((x1 * y2 - y1 * x2) + (x2 * y3 - y2 * x3) + (x3 * y4 - y3 * x4) + (x4 * y1 - y4 * x1));
        squareResult /= 2;
        return squareResult;
    }

}
