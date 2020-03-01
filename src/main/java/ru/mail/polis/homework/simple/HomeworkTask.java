package ru.mail.polis.homework.simple;

import java.util.function.ToDoubleFunction;

public class HomeworkTask {

    /**
     * Нужно численно посчитать интеграл от a до b с шагом delta от функции function
     * Для того, что бы получить значение по Y для точки X, надо просто написать function.applyAsDouble(t)
     * Считаем, что функция определена на всем пространстве от a до b
     */
    public static double calcIntegral(double a, double b, ToDoubleFunction<Double> function, double delta) {
        double x = a;
        double integral = 0;
        while (x+delta<b){
            x += delta;
            integral += delta*(function.applyAsDouble(x)+function.applyAsDouble(x-delta))/2;
        }
        return integral;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        byte i = 1; long max = 0; // Хотел сделать byte max, но тогда в условном операторе не
        while (a>0){              // позволяет нормально присвоить  max = a%10. Приходится делать
            if (a%10 >= max){     // двойное приведение к байту: (byte)((byte)a%10). Как это понимать?
                max = a%10;

                i = 1;
            }
            else{
                i++;
            }
            a /= 10;
        }
        return i;
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        return (double)(y2-y1)/(double)(x2-x1)*x3 + (y1 - (double)(y2-y1)/(double)(x2-x1)*x1);
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double d1 = DoubleTask.length(x1, y1, x3, y3);
        double d2 = DoubleTask.length(x2, y2, x4, y4);
        if ((d1 < 1e-2) || (d2 < 1e-2)) {
            return 0.0;
        };
        return 0.5*d1*d2*Math.abs((double)((x3 - x1)*(y4 - y2) - (x4-x2)*(y3-y1)))/(d1*d2);
    }   // Последнее выражение в произведении -- синус угла между диагоналями

}
