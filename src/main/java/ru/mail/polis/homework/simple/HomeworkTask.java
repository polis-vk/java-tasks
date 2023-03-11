package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double sum = 0;
        double x = a;
        while (x < b) {
            sum += function.applyAsDouble(x) * delta;
            x += delta;
        }
        return sum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte index = 1;
        byte maxDigit = 0;
        byte count = 1;
        long divider = 1;
        long input = a;
        while (input / 10 != 0) {
            divider *= 10;
            input /= 10;
        }
        input = a;
        do {
            byte temp = (byte) Math.abs((input / divider) % 10);
            if (temp > maxDigit) {
                maxDigit = temp;
                index = count;
            }
            if (maxDigit == 9) {
                break;
            }
            divider /= 10;
            count++;
        } while (divider != 0);
        return index;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        if (x1 != x2) {
            return y1 - ((double) ((x1 - x3) * (y1 - y2)) / (x1 - x2));   //вывод формулы из равенста отношений (x1 - x3) / (x1 - x2) = (y1 -y3) / (y1 - y2)
        }
        return (double) (y1 + y2) / 2;    //бесконечно много решений при x1 = x2 (как частное решение - точка по середине)
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        return 0.5 * Math.abs((x1 - x2) * (y1 + y2) + (x2 - x3) * (y2 + y3) + (x3 - x4) * (y3 + y4) + (x4 - x1) * (y4 + y1)); //Площадь S произвольного не самопересекающегося четырёхугольника
    }

}
