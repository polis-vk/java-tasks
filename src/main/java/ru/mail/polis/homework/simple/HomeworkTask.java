package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integralValue = 0;
        for (double currentVal = a; currentVal <= b; currentVal += delta) {
            integralValue += delta * function.applyAsDouble(currentVal);
        }
        return integralValue;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte numb = 0;
        long maxValue = 0;
        long currentValue;
        byte i = 1;
        while (a > 0) {
            currentValue = (a % 10);
            if (currentValue > maxValue) {
                maxValue = currentValue;
                numb = i;
            }
            a /= 10;
            i++;
        }
        return (byte) (i - numb);
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (double) ((x3 - x1) * (y2 - y1)) / (x2 - x1) + y1;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    /**
     * Для нвхождения площади четырёхугольника азбил его на два треугольника и по формулам Герона посчитал их площадь,
     * а затем сложил
     * s = sqrt (p * (p-a) * (p-b) * (p-c)), где p - полупериметр треугольника
     * Так же, для избежания дублирования кода, использовал ранее реализованную функцию для нахождения расстояния
     * между двумя точками
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        float AB = DoubleTask.length(x1, y1, x2, y2);
        float BC = DoubleTask.length(x2, y2, x3, y3);
        float CD = DoubleTask.length(x3, y3, x4, y4);
        float DA = DoubleTask.length(x4, y4, x1, y1);
        float AC = DoubleTask.length(x1, y1, x3, y3);
        double perimetre1 = (AB + BC + AC) / 2;
        double perimetre2 = (CD + DA + AC) / 2;
        double area1 = Math.sqrt(perimetre1 * (perimetre1 - AB) * (perimetre1 - BC) * (perimetre1 - AC));
        double area2 = Math.sqrt(perimetre2 * (perimetre2 - CD) * (perimetre2 - DA) * (perimetre2 - AC));
        return area1 + area2;
    }

}
