package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        int j = (int)((b-a)/delta);
        double integral = function.applyAsDouble(a);
        for (int i =1; i<=j; i++) {
            integral = integral + function.applyAsDouble(a+i*delta);
        }
        integral = integral*delta;
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {

        int num =0;
        int x = (int)Math.log10((double)a);
        long d = 1;
        for (int i =1; i<=x; i++) {
            d = d*10;
        }
        int max =0;
        int y =0;
        byte i =0;
        byte n=1;
        do {
            y = (int) ((a/d)%10);
            i++;
            if (y>max) {
                max=y;
                n = i;
            }
            d=d/10;
        } while ((max<9)&&(d>=1));
        return n;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {

        if (y1==y2) {
            return y1;
        } else {
            double k = ((double) (y1-y2))/((double) (x1-x2));
            double b = y1 - x1*k;
            double y3 = k*x3+b;
            return y3;
        }

    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {

        double a = Math.sqrt((x1-x2)*(x1-x2)+(y1-y2)*(y1-y2));
        double b = Math.sqrt((x2-x3)*(x2-x3)+(y2-y3)*(y2-y3));
        double c = Math.sqrt((x1-x3)*(x1-x3)+(y1-y3)*(y1-y3));
        double p = (a+b+c)/2;
        double s1 = Math.sqrt(p*(p-a)*(p-b)*(p-c));
        a = Math.sqrt((x3-x4)*(x3-x4)+(y3-y4)*(y3-y4));
        b = Math.sqrt((x1-x4)*(x1-x4)+(y1-y4)*(y1-y4));
        p = (a+b+c)/2;
        s1 = s1+Math.sqrt(p*(p-a)*(p-b)*(p-c));
        return s1;
    }

}
