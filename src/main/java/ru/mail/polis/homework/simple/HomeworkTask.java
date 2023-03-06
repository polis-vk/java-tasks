package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        return 0;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        String aStr = String.valueOf(a);
        byte aMax = 0;
        if (a < 0) {
            for (int i = 1; i < aStr.length(); i++) {
                byte tempByteVar = (byte) aStr.charAt(i);
                if (aMax < tempByteVar) {
                    aMax = tempByteVar;
                }
            }
        } else {
            for (int i = 0; i < aStr.length(); i++) {
                byte tempByteVar = Byte.parseByte(String.valueOf(aStr.charAt(i)));
                if (aMax < tempByteVar) {
                    aMax = tempByteVar;
                }
            }
        }
        if (a >= 0) {
            return (byte) (aStr.indexOf(String.valueOf(aMax)) + 1);
        } else {
            return (byte) (aStr.indexOf(String.valueOf(aMax)) + 2);
        }
    }
        /*
        int maxA = -1;
        String aStr = String.valueOf(a);
        String[] arrayOfDigits = aStr.split("");
        for (int i = 0; i < arrayOfDigits.length; i++) {
            byte tempVar = 0;
            try {
                 tempVar = Byte.parseByte(arrayOfDigits[i]);
            } catch (NumberFormatException e) {

            }
            if (maxA < tempVar) {
                maxA = tempVar;
            }
        }
        if (a < 0) {
            return (byte) (aStr.indexOf(maxA) + 1);
        } else {
            return (byte) aStr.indexOf(maxA);
        }

    }
    */


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return 0;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return 0;
    }

}
