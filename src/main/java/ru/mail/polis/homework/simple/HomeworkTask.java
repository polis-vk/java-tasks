package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    // Метод прямоугольников где одна сторона = шаг, а вторая значение по Y для точки X, что равняется высоте
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double result = 0;
        for (double i = a; i <= b; i += delta) {
            result += delta * function.applyAsDouble(i);
        }
        return result;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        int currentMax = 0;
        int currentNumber;
        int resultIndexEnd = 0;
        int counts = 0;
        while (a > 0) {
            counts += 1;
            currentNumber = (int) (a % 10);
            resultIndexEnd = currentNumber >= currentMax ? counts : resultIndexEnd;
            currentMax = Math.max(currentMax, currentNumber);
            a /= 10;
        }

        return (byte) (counts - resultIndexEnd + 1);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    // Уравнение прямой для 3-х точек : (x_3 - x_1) / (x_2 - x_1) = (y_3 - y_1) / (y_2 - y_1)
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return ((double) (x3 - x1) * (y2 - y1) / (x2 - x1)) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    // Сумма площадей 2-ух треугольников, разделенных диагональю
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return Math.abs((double) ((x1 - x2) * (y3 - y2) - (y1 - y2) * (x3 - x2)) / 2)
                + Math.abs((double) ((x1 - x3) * (y4 - y3) - (y1 - y3) * (x4 - x3)) / 2);
    }

}
