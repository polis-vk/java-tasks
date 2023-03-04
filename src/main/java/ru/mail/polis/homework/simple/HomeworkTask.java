package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        // Метод прямоугольников
        double temp = a;
        double result = 0;
        int i = 0;
        while(a <= b){
            i++;
            result += function.applyAsDouble(a);
            a+=delta;
        }
        result *= (b - temp) / i;
        return result;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        // Число переводится в строку для удобной поэлементной обработки числа
        String str = String.valueOf(a);
        int maxIndex = -1;
        int max = -1;
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) > max + 48) {
                max = str.charAt(i) - 48;
                maxIndex = i + 1;
            }
        }
        if(str.charAt(0) == '-') {
            maxIndex--;
        }
        return (byte) maxIndex;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        // Функция выведена в соответствии каноническим уровнением прямой
        double expression = (double)((x3 - x1) * y2 + (x2 - x3) * y1)/(x2-x1);
        return y2 == y1 ? 1 : expression;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        // диагональ AC
        double diagonal = DoubleTask.length(x1, y1, x3, y3);
        // стороны треугольника ABC
        double sideAB = DoubleTask.length(x1, y1, x2, y2);
        double sideBC = DoubleTask.length(x2, y2, x3, y3);
        // стороны треугольника CDA
        double sideCD = DoubleTask.length(x3, y3, x4, y4);
        double sideDA = DoubleTask.length(x4, y4, x1, y1);
        // полумериметры треугольников
        double pABC = (sideAB + sideBC + diagonal) / 2;
        double pCDA = (sideCD + sideDA + diagonal) / 2;
        // площади треугольников
        double squareABC = Math.sqrt(pABC * (pABC - sideAB) * (pABC-sideBC) * (pABC-diagonal));
        double squareCDA = Math.sqrt(pCDA * (pCDA - sideCD) * (pCDA-sideDA) * (pCDA-diagonal));
        return squareABC + squareCDA;
    }
}
