package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double result = 0;
        double curr = a;
        while (curr < b) {
            result += function.applyAsDouble(curr) * delta;
            curr += delta;
        }
        return result;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        String strNumber = Long.toString(a);
        int pointOfMax = -1;
        int maxNumber = -1;
        for (int i = 0; i < strNumber.length(); i++) {
            int number = Integer.parseInt(strNumber.substring(i, i+1));
            if (number > maxNumber){
                pointOfMax = i;
                maxNumber = number;
            }
        }
        return (byte)pointOfMax;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double x11 = Double.valueOf(x1);
        double y11 = Double.valueOf(y1);
        double x22 = Double.valueOf(x2);
        double y22 = Double.valueOf(y2);
        double x33 = Double.valueOf(x3);
        return (((x33 - x11) * (y22 - y11)) / (x22 - x11)) + y11;
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
