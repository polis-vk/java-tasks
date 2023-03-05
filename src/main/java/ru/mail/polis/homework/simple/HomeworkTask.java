package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

import static ru.mail.polis.homework.simple.DoubleTask.length;
import static ru.mail.polis.homework.simple.IntegerTask.kDecimal;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double result = 0;
        for (int i = 1; i < (b - a) / delta; i++) {
            result += function.applyAsDouble(a + i * delta);
        }
        return delta * (function.applyAsDouble(a) / 2 + result + function.applyAsDouble(b) / 2);
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte length = (byte) (Math.log10(a) + 1);
        byte maxValue = 0;
        byte maxValuePosition = 1;
        for (byte i = length; i > 0; i--){
            if (maxValue < kDecimal(a, i)){ // функция из IntegerTask
                maxValue = (byte) kDecimal(a, i);
                maxValuePosition = (byte) (length - i + 1);
            }
        }
        return maxValuePosition;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return y1 + (x3 - x1) * (y2 - y1) / (double) (x2 - x1);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double ab = length(x1, y1, x2, y2);
        double bc = length(x2, y2, x3, y3);
        double cd = length(x3, y3, x4, y4);
        double da = length(x4, y4, x1, y1);
        double bd = length(x2, y2, x4, y4);
        double semiPerimeter = (ab + bc + cd + da) / 2.0;
        if (ab == 0 || bc == 0 || cd == 0 || da == 0 || bd == 0) {
            return 0;
        }
        double acos1 = Math.acos((da * da + ab * ab - bd * bd) / (2.0 * da * ab));
        double acos2 = Math.acos((cd * cd + bc * bc - bd * bd) / (2.0 * cd * bc));
        double cos = Math.cos((acos1 + acos2) / 2.0);
        return Math.sqrt((semiPerimeter - ab) * (semiPerimeter - bc) * (semiPerimeter - cd) * (semiPerimeter - da) - ab * bc * cd * da * cos * cos);
    }

}
