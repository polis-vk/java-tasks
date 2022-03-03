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
        for (double t = a; t < b; t += delta) {
            calcIntegral += function.applyAsDouble(t) * delta;
        }
        return calcIntegral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte buffer = 0;
        byte maxNum = 0;
        byte count = 1;
        long cloneA = a;
        while (cloneA != 0) {
            if (Math.abs(cloneA % 10) >= maxNum) {
                maxNum = (byte) (cloneA % 10);
                buffer = count;
            }
            cloneA /= 10;
            count++;
        }
        return (byte) (count - buffer);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (((x3 - x1) * (y2 - y1) * 1.) / (x2 - x1)) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double sTriangle1 = Math.abs((x1 - x2) * (y3 - y2) - (y1 - y2) * (x3 - x2)) / 2.;
        double sTriangle2 = Math.abs((x1 - x3) * (y4 - y3) - (y1 - y3) * (x4 - x3)) / 2.;
        return sTriangle1 + sTriangle2;
    }

}
