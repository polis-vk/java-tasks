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
        return 0;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long maxDigit =  -1;
        int number =  0;
        int count = 0;

        while (a > 0) {
            if (a % 10 >= maxDigit) {
                maxDigit = a % 10;
                number = count + 1;
            }
            a = a / 10;
            count++;
        }
        number = count - number + 1;
        return (byte) number;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
         return 0;
    }
    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double line(int a1, int b1, int a2, int b2) {
        return Math.sqrt((a2 - a1) * (a2 - a1) + (b2 - b1) * (b2 - b1));
    }
    public static double halfPerimeter(double a, double b, double c) {
        return (a + b + c) / 2;
    }
    public static double triangleSquare(double p, double a, double b, double c) {
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double ab = line(x1, y1, x2, y2);
        double ac = line(x1, y1, x3, y3);
        double ad = line(x1, y1, x4, y4);
        double bc = line(x2, y2, x3, y3);
        double cd = line(x3, y3, x4, y4);
        double p1 = halfPerimeter(ab, ac, bc);
        double s1 = triangleSquare(p1, ab, ac, bc);
        double p2 = halfPerimeter(ad, ac, cd);
        double s2 = triangleSquare(p2, ad, ac, cd);
        return s1 + s2;
    }

}
