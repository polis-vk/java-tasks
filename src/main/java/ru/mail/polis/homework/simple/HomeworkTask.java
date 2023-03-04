package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double i = a, sum = 0.0;
        while (i < b) {
            sum += function.applyAsDouble(i) * delta;
            i += delta;
        }
        return sum;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte curIndex = 0, maxIndex = 0;
        long maxDigit = 0, curDigit = 0;
        while (a != 0) {
            curIndex++;
            curDigit = a % 10;
            if (curDigit > maxDigit) {
                maxDigit = curDigit;
                maxIndex = curIndex;
            }
            a /= 10;
        }
        maxIndex = (byte) (curIndex - maxIndex + 1);
        return maxIndex;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return y1 + (double)((y2 - y1) * (x3 - x1)) / (double)(x2 - x1);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double module_d1 = Math.sqrt(Math.pow(x3 - x1, 2) + Math.pow(y3 - y1, 2));
        double module_d2 = Math.sqrt(Math.pow(x4 - x2, 2) + Math.pow(y4 - y2, 2));
        if (!(module_d2 == 0 || module_d1 == 0)) {
            double cosAlfa = Math.abs((x3 - x1) * (x4 - x2) + (y3 - y1) * (y4 - y2)) / (module_d1 * module_d2); //using cosAlfa = |v_A * v_B| / (m_A * m_B)
            double sinAlfa = Math.sqrt(1 - Math.pow(cosAlfa, 2));
            return 0.5 * module_d1 * module_d2 * sinAlfa;
        } else {
            return 0.0;
        }
    }

}
