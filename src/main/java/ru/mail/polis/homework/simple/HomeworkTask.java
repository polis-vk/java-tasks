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
        for (double x = a; x <= b; x += delta) {
            integral += function.applyAsDouble(x) * delta;
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte indexOfMaxDigit = 1; //Считаем, что первая цифра(самая правая) - максимальная
        byte indexOfDigit = 2;    //Следующая рассматривая цифра имеет индекс 2
        long maxDigit = a % 10;
        long copyA = a / 10;
        byte size = 1;
        while (copyA != 0) {
            long digit = copyA % 10;
            if (digit >= maxDigit) { //ищем последную максимальную цифру так как порядок инвертирован
                indexOfMaxDigit = indexOfDigit;
                maxDigit = digit;
            }
            copyA /= 10;
            indexOfDigit++;
            size++;
        }
        return (byte) (size - indexOfMaxDigit + 1); //меняем порядок индексирования, byte + byte = int
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double tan = (y2 - y1) / (double) (x2 - x1);
        return tan * (x3 - x1) + y1;
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
