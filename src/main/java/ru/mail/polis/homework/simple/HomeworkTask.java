package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double curr = a;
        double answer = 0;
        while (curr < b) {
            answer += function.applyAsDouble(curr) * delta;
            curr += delta;
        }
        return answer;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        //Надеюсь, что сам "обход" мы можем сделать справа на лево, а слово "Счет" лишь подразумевает
        //Что мы должны делать >= проверку, а не просто >, чтобы найти первый элемент, что для нас будет последним
        byte max = 0;
        byte counter = 0;
        if (a == 0) {
            return 1;
        }
        long curr = a;
        while (curr != 0) {
            byte lastNum = (byte) (curr % 10);
            if (lastNum >= max) {
                max = lastNum;
                counter = 0;
            }
            curr /= 10;
            counter += 1;
        }

        return counter;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double yLen = y2 - y1;
        double xLen = x2 - x1;
        if (xLen == 0) {
            throw new IllegalArgumentException("Points should be located differently in x axis");
        }
        double k = yLen / xLen;
        double b = y1 - k * x1;

        return k * x3 + b;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {

        int maxX = Math.max(Math.max(x1, x2), Math.max(x3, x4));
        int minX = Math.min(Math.min(x1, x2), Math.min(x3, x4));

        int maxY = Math.max(Math.max(y1, y2), Math.max(y3, y4));
        int minY = Math.min(Math.min(y1, y2), Math.min(y3, y4));

        int maxLenX = maxX - minX;
        int maxLenY = maxY - minY;

        double area = maxLenX * maxLenY;

        int lengthX = Math.abs(x1 - x2);
        int lengthY = Math.abs(y1 - y2);
        area -= ((double) (lengthX * lengthY)) / 2.0;


        lengthX = Math.abs(x2 - x3);
        lengthY = Math.abs(y2 - y3);
        area -= ((double) (lengthX * lengthY)) / 2.0;

        lengthX = Math.abs(x3 - x4);
        lengthY = Math.abs(y3 - y4);
        area -= ((double) (lengthX * lengthY)) / 2.0;

        lengthX = Math.abs(x4 - x1);
        lengthY = Math.abs(y4 - y1);
        area -= ((double) (lengthX * lengthY)) / 2.0;

        //Since rectangle is convex, we can certainly say that outer rect will be less then inner one

        if ((x1 != maxX && x1 != minX) && (y1 != maxY && y1 != minY)) {
            lengthX = Math.abs(x1 - x2);
            lengthY = Math.abs(y1 - y4);
            double rec1 = lengthX * lengthY;
            lengthX = Math.abs(x1 - x4);
            lengthY = Math.abs(y1 - y2);
            double rec2 = lengthX * lengthY;
            area -= Math.min(rec1, rec2);
        }

        if ((x2 != maxX && x2 != minX) && (y2 != maxY && y2 != minY)) {
            lengthX = Math.abs(x2 - x3);
            lengthY = Math.abs(y2 - y1);
            double rec1 = lengthX * lengthY;
            lengthX = Math.abs(x2 - x1);
            lengthY = Math.abs(y2 - y3);
            double rec2 = lengthX * lengthY;
            area -= Math.min(rec1, rec2);
        }

        if ((x3 != maxX && x3 != minX) && (y3 != maxY && y3 != minY)) {
            lengthX = Math.abs(x3 - x2);
            lengthY = Math.abs(y3 - y4);
            double rec1 = lengthX * lengthY;
            lengthX = Math.abs(x3 - x4);
            lengthY = Math.abs(y3 - y2);
            double rec2 = lengthX * lengthY;
            area -= Math.min(rec1, rec2);
        }

        if ((x4 != maxX && x4 != minX) && (y4 != maxY && y4 != minY)) {
            lengthX = Math.abs(x4 - x3);
            lengthY = Math.abs(y4 - y1);
            double rec1 = lengthX * lengthY;
            lengthX = Math.abs(x4 - x1);
            lengthY = Math.abs(y4 - y3);
            double rec2 = lengthX * lengthY;
            area -= Math.min(rec1, rec2);
        }

        return area;
    }
}
