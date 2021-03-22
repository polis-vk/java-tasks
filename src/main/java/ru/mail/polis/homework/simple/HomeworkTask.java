package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

import java.lang.Math;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double integ = 0;

        for (double i = a; i <= b; i += delta) {
            integ += function.applyAsDouble(i) * delta;
        }
        return integ;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        String str;
        byte max = 0;
        byte n = 0;
        
        str = String.valueOf(Math.abs(a)); 
        for (byte i = 0; i < str.length(); i++){
            byte c = (byte) str.charAt(i);
            if (c > max) {
                max = c;
                n = i;
            }
        }
        return ++n;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double y3 = 0;

        if (x1 != x2) {
            y3 = (double) (x3 - x1) / (x2 - x1) * (y2 - y1) + y1;
        }
        
        return y3;
    }

    /*
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        float length = (float) Math.sqrt(Math.pow(x3 - x1, 2)+Math.pow(y3 - y1, 2));
        flaot yH1 = 
        return 0; */
    
}
