package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double square = 0;
        for (double i = a + delta; i <= b; i += delta) {
            double side1 = function.applyAsDouble(i - delta);//боковая сторона прямоугольной трапеции 1
            double side2 = function.applyAsDouble(i);//боковая сторона прямоугольной трапеции 2
            double side3 = side1 < side2 ? side1 : side2;//находим меньшее основание трапеции
            square += side3 * delta + 0.5 * Math.abs(side1 - side2) * delta;
            //прибавляем площадь прямоугольной трапеции с высотой равной шагу
            //площадь трапеции состоит из суммы площадей прямоугольника и треугольника
        }
        return square;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        int length = String.valueOf(a).length();//количество цифр числа
        long max = 1;//максимальная цифра
        byte number = 1;//порядковый номер максимальной цифры числа
        for (int i = 0; i <= length; i++) {
            if (a % 10 >= max) {
                max = a % 10;
                number = (byte) (length - i);
            }
            a /= 10;
        }
        return number;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3)//решаем пропорцией подобия сторон треугольников
    {
        double y3 = (y1 - y2) * (x1 - x3);
        y3 = y1 - (y3 / (x1 - x2));
        return y3;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double diag1 = Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2));
        double diag2 = Math.sqrt(Math.pow(x2 - x4, 2) + Math.pow(y2 - y4, 2));
        double square = 0;
        if (diag1 != 0 && diag2 != 0) {
            double cosinus = ((x1 - x3) * (x2 - x4) + (y1 - y3) * (y2 - y4)) / (Math.sqrt(Math.pow(x1 - x3, 2) + Math.pow(y1 - y3, 2)) * Math.sqrt(Math.pow(x2 - x4, 2) + Math.pow(y2 - y4, 2)));
            square = 0.5 * diag1 * diag2 * Math.sqrt(1 - Math.pow(cosinus, 2));//вычисляем по формуле через произведение диагоналей и синус
        }
        return square;

    }

}
