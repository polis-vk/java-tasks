package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integral = 0;
        double currentX = a;
        while (currentX < b) {
            integral += function.applyAsDouble(currentX) * delta;
            currentX += delta;
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        long maxDigit = 0;
        long numberOfMaxDigit = 0;
        long currentDigit;
        long countOfDigits;
        long tempValue = a;
        for (countOfDigits = 1; tempValue > 0; countOfDigits++) {
            currentDigit = tempValue % 10;
            tempValue = tempValue / 10;
            if (currentDigit >= maxDigit) {
                maxDigit = currentDigit;
                numberOfMaxDigit = countOfDigits;
            }
        }
        return (byte) (countOfDigits - numberOfMaxDigit);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (double) ((x2 * y1 - x1 * y2) - (y1 - y2) * x3) / (x2 - x1);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     * <p>
     * Четырехугольник разбивается на два треугольника, площади каждого из которых считается по формуле Герона, а затем
     * они суммируются.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double ab = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        double bc = Math.sqrt((x2 - x3) * (x2 - x3) + (y2 - y3) * (y2 - y3));
        double cd = Math.sqrt((x3 - x4) * (x3 - x4) + (y3 - y4) * (y3 - y4));
        double da = Math.sqrt((x4 - x1) * (x4 - x1) + (y4 - y1) * (y4 - y1));
        double ac = Math.sqrt((x1 - x3) * (x1 - x3) + (y1 - y3) * (y1 - y3));

        double p1 = (ab + bc + ac) / 2;
        double p2 = (ac + cd + da) / 2;
        double s1 = Math.sqrt(p1 * (p1 - ab) * (p1 - bc) * (p1 - ac));
        double s2 = Math.sqrt(p2 * (p2 - ac) * (p2 - cd) * (p2 - da));

        return s1 + s2;
    }

}
