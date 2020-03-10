package ru.mail.polis.homework.simple;


import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        function.applyAsDouble(4d);
        double result = 0;
        for (int i = 0; i < (b - a) / delta; i++) {
            result += function.applyAsDouble(a + delta * (i - 1 / 2));
        }
        return result * delta;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {

        byte max = -1;
        byte currentIndex = 1;
        while (a > 0) {
            if ((byte) ((long) a % 10) >= max) {
                max = (byte) ((long) a % 10);
                currentIndex = 1;
            } else {
                currentIndex++;
            }
            a = a / 10;
        }
        return currentIndex;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (double) (x3 - x1) * (y2 - y1) / (x2 - x1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double sideOfSquare1 = Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
        double sideOfSquare2 = Math.sqrt(Math.pow(x2 - x3, 2) + Math.pow(y2 - y3, 2));
        double sideOfSquare3 = Math.sqrt(Math.pow(x3 - x4, 2) + Math.pow(y3 - y4, 2));
        double sideOfSquare4 = Math.sqrt(Math.pow(x4 - x1, 2) + Math.pow(y4 - y1, 2));
        double halfPerimeter = Math.sqrt(sideOfSquare1 + sideOfSquare2 + sideOfSquare3 + sideOfSquare4) / 2;
        return Math.sqrt((halfPerimeter - sideOfSquare1) * (halfPerimeter - sideOfSquare2) * (halfPerimeter - sideOfSquare3) * (halfPerimeter - sideOfSquare4));
    }

}
