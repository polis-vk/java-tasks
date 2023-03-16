package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta)
    {
        double integralValue = 0;
        for (double X = a; X < b; X += delta)
        {
            integralValue += function.applyAsDouble(X);
        }
        return integralValue * delta;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a)
    {
        if(a != 0){ // Делаем проверку на 0, так как логарифм от 0 не определён
            byte numberLength = (byte) (Math.log10(a) + 1);
            byte maxFigureIndex = 1;
            byte maxFigure = 0;
            byte figure;
            for (byte figureIndex = numberLength; figureIndex > 0; figureIndex--)
            {
                figure = (byte) (a % 10L);
                if (figure >= maxFigure)
                {
                    maxFigure = figure;
                    maxFigureIndex = figureIndex;
                }
                a /= 10L;
            }
            return maxFigureIndex;
        } else { return 1; }
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return y1 + (y2 - y1) * (x3 - x1) / (double) (x2 - x1);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return 0.5 * Math.abs((x2 - x1) * (y3 - y1) - (x3 - x1) * (y2 - y1)) + 0.5 * Math.abs((x4 - x1) * (y3 - y1) - (x3 - x1) * (y4 - y1));
        // Разбиваем четырехугольника на два треугольника и находим их площадь
    }

}
