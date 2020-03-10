package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        //Метод Симпсона
        double h = (b - a) / ((Math.abs(a) + Math.abs(b)) / delta);
        double result = 0;
        double x = 0;
        x = a + h;
        while (x < b){
            result = result + 4 * function.applyAsDouble(x);
            x = x + h;
            result = result + 2 * function.applyAsDouble(x);
            x = x + h;
        }
        result = h / 3 * (result + function.applyAsDouble(a) - function.applyAsDouble(b));
        return result;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        int[] arr;
        int count =0;
        long b = a;
        while (b != 0){
            count++;
            b /= 10;
        }
        arr = new int[count];
        int max = 0;
        for (int i = arr.length - 1; i >=0; i--){
            arr[i] = (int) a % 10;
            a /= 10;
        }
        for (int j = 0; j < arr.length; j++){
            if (arr[j] > max){
                max = arr[j];
                count = j;
            }
        }
        return (byte) (count + 1);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (double)((x3 - x1) * (y2 - y1)) / (x2 - x1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double ab = tPif(x1, y1, x2, y2);
        double bc = tPif(x2, y2, x3, y3);
        double cd = tPif(x3, y3, x4, y4);
        double ad = tPif(x4, y4, x1, y1);

        double ac = tPif(x3, y3, x1, y1);


        double p1 = halfPerim(ab, bc, ac);
        double s1 = square(p1, ab, bc, ac);

        double p2 = halfPerim(ad, cd, ac);
        double s2 = square(p2, ad, cd, ac);

        return s1 + s2;
    }

    private static double square(double p, double ab, double bc, double ac) {
        return Math.sqrt(p * (p - ab) * (p - bc) * (p - ac));
    }

    private static double halfPerim(double ab, double bc, double ac) {
        return (ab+bc+ac)/2;
    }

    public static double tPif(int x1, int y1, int x2, int y2){
        if (x2 == x1)
        {
            return Math.abs(y2 - y1);
        }
        if (y2 == y1)
        {
            return Math.abs(x2 - x1);
        }
        return Math.sqrt( (double) Math.pow(Math.abs(x2 - x1), 2) + (double) Math.pow(Math.abs(y2 - y1), 2));
    }

}
