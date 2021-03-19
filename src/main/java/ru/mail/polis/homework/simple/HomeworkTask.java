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
        double integral = 0;
        double x; // текущее значение x
        x = a;
        while (x<b){
            integral += function.applyAsDouble(x) * delta;
            x += delta;
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte max = 0; // текущая максимальная цифра
        byte number = 0; // номер текущей максимальной цифры (счтая с конца)
        byte k = 0; // количество цифр в числе
        while (a > 0){
            k++;
            if ((a % 10) >= max){
                max = (byte) (a % 10);
                number = k;
            }
            a = a / 10;
        }
        if (k == 0){
            number = 1;
        }
        else {
            number = (byte) ( (k - number) + 1);
        }

        return number;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double y3;
        double k; // коэфициент наклона прямой
        double b; // свободный член

        k = (y2 - y1) / (double) (x2 - x1);
        b = y1 - k * x1;

        y3 = k * x3 + b;
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
