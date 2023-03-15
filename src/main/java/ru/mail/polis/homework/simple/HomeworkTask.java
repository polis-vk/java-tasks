package ru.mail.polis.homework.simple;

import javax.naming.NameNotFoundException;
import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double result = 0;
        double step = ((b - a) / delta);
        for(double i = 0; i <= b - a; i += delta) {
            result += function.applyAsDouble(a + i) * delta;
        }
        return result;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte maxNumber = -1;
        byte numberPosition = 0;
        byte maxNumberPosition = 0;
        byte number = -1;
        while(a != a % 10) {
            numberPosition++;
            number = (byte) Math.abs(a % 10);
            a = a / 10;
            if(number >= maxNumber) {
                maxNumber = number;
                maxNumberPosition = numberPosition;
            }
        };
        numberPosition++;
        if(Math.abs(a) >= maxNumber) {
            maxNumber = number;
            maxNumberPosition = numberPosition;
        }
        numberPosition = (byte) (numberPosition - maxNumberPosition + 1);
        return numberPosition;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double y3 = (double) (x3 - x1) * (y2 - y1) / (x2 - x1) + y1;
        return y3;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double a = Math.sqrt(Math.pow(x1 - x2, 2) + Math.pow(y1 - y2, 2));
        double b = Math.sqrt(Math.pow(x3 - x2, 2) + Math.pow(y3 - y2, 2));
        double c = Math.sqrt(Math.pow(x4 - x3, 2) + Math.pow(y4 - y3, 2));
        double d = Math.sqrt(Math.pow(x1 - x4, 2) + Math.pow(y1 - y4, 2));

        double diag = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));
        double firstHalfP = (a + b + diag) / 2;
        double firstSquare = Math.sqrt(firstHalfP * (firstHalfP - a) * (firstHalfP - b) * (firstHalfP - diag));

        double secondHalfP = (c + d + diag) / 2;
        double secondSquare = Math.sqrt(secondHalfP * (secondHalfP - c) * (secondHalfP - d) * (secondHalfP - diag));
        return secondSquare + firstSquare;
    }

}
