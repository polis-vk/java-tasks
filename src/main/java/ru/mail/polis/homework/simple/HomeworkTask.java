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
        double n = (b - a) / delta;

        for (int i = 0; i < n; i++) {
            result += function.applyAsDouble(a + delta * (i + 0.5));
        }
        result *= delta;
        return result;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        String number = Long.toString(a);
        byte cout = 0;
        int max = number.charAt(cout) - '0';
        int i = 1;
        if (a < 0) {
            i++;
            max = number.charAt(cout + 1) - '0';
        }

        for (; i < number.length(); i++) {
            if (max == 9) break;
            if (number.charAt(i) - '0' > max) {
                max = number.charAt(i) - '0';
                cout = (byte) i;
            }
        }

        return (byte) (cout + 1);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        //Выводим из канонического уравнения прямой
        double y = (double) ((x3 - x1) * (y2 - y1) + y1 * (x2 - x1)) / (x2 - x1);
        return y;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        //Проверяем четырёхугольник на выпуклость
        if (Math.abs(sq(x1,y1,x2,y2,x3,y3)-sq(x4,y4,x2,y2,x3,y3)-sq(x1,y1,x4,y4,x3,y3)-sq(x1,y1,x2,y2,x4,y4))<1e-5){
            return 0;
        } else {
        //Сумма площадей 2 треугольников
            double square = (double) Math.abs((x1 - x2) * (y3 - y2) - (y1 - y2) * (x3 - x2)) /2
                    + (double) Math.abs((x1 - x3) * (y4 - y3) - (y1 - y3) * (x4 - x3)) /2;
            return square;
        }
    }

    private static double sq(int a, int b, int c, int d, int e, int f){
        return (double) Math.abs(a * (d - f) + c * (f - b) + e * (b - d)) /2;
    }
}
