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
            integral += function.applyAsDouble(i);
        }
        return delta * integral;
    }
    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long max = 0;
        int i_max = 0;  // номер максимального числа с конца
        int j = 1;  // номер текущей цифры и в конце while'а - кол-во цифр в числе
        while (a != 0) {
            if (Math.abs(a) % 10 >= max) {
                max = Math.abs(a % 10);
                i_max = j;
            }
            a = a / 10;  // уменьшаем число
            j++;
        }
        return (byte)(j - i_max);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (y1 + (y2 - y1) * (double)(x3 - x1) / (double)(x2 - x1));
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double AreaABC, AreaACD; // площади ABC и ACD
        AreaABC = Math.abs(0.5 * (x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2))); // выводится из курса аналитической геометрии
        AreaACD = Math.abs(0.5 * (x1 * (y4 - y3) + x4 * (y3 - y1) + x3 * (y1 - y4)));


        return AreaABC + AreaACD;
    }

}