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
        double res=0;
        double lower=a,upper=a+delta;
        while (upper<b) {
            res+=(upper-lower)*(double)(function.applyAsDouble(lower)+function.applyAsDouble(upper))/2;
            lower=upper;
            upper+=delta;
        }
        return res;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte cnt=0;
        byte index=0;
        long max=0;
        while (a>0) {
            long tmp=a%10;
            cnt++;
            if (max<=tmp) {
                max=tmp;
                index=cnt;
            }
            a=a/10;
        }
        return (byte)(Math.abs(cnt-index)+1);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double res=0;
        res=Math.abs((double)(Math.abs(y1-y2)*Math.abs(x1-x3))/Math.abs(x1-x2)-y1);
        if ((y1-y2)*x3+(x2-x1)*res+(x1*y2-x2*y1)==0) return res;
        else return -res;
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
