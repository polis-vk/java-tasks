package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double sqr = 0;
        for (; a < b; a = a + delta) {
            sqr += delta * function.applyAsDouble(a);
        }
        return sqr;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {

        int b;
        long h = a;
        int k = 0;
        int kmax = 0;
        int max = 0;
        int qtt = (int) Math.log10(h) + 1;
        if (qtt == 1){
            return 1;
        }
        while (h != 0) {
            b = (int) h % 10;
            if (b >= max) {
                max = b;
                kmax = k;
            }
            h /= 10;
            k++;
        }
        return (byte) (qtt - kmax);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (double) ((x3 - x1) * (y2 - y1) + y1 * (x2 - x1)) / (x2 - x1);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double ab = Math.sqrt(Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2));
        double bc = Math.sqrt(Math.pow((x2 - x3), 2) + Math.pow((y2 - y3), 2));
        double cd = Math.sqrt(Math.pow((x3 - x4), 2) + Math.pow((y3 - y4), 2));
        double da = Math.sqrt(Math.pow((x4 - x1), 2) + Math.pow((y4 - y1), 2));
        double ac = Math.sqrt(Math.pow((x1 - x3), 2) + Math.pow((y1 - y3), 2));
        double p1 = (ab + bc + ac) / 2;
        double p2 = (cd + da + ac) / 2;
        double s1 = Math.sqrt(p1 * (p1 - ab) * (p1 - bc) * (p1 - ac));
        double s2 = Math.sqrt(p2 * (p2 - cd) * (p2 - da) * (p2 - ac));

        return s1 + s2;
    }

}
