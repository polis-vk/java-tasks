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
        double ans = 0;
        while (x < b) {
            ans += delta * function.applyAsDouble(x);   //По методу правых прямоугольников суммируем площади delta * Y
            x += delta;
        }
        return ans;
    }

    /**
     * Вывести номер максимальной цифры. Счет начинается слева направо,
     * выводим номер первой максимальной цифры (если их несколько)
     */
    public static byte maxNumber(long a) {
        if (a == 0) {                                   //Если число равно нулю,
            return 1;                                   //то максимальная цифра стоит на 1 позиции
        }
        long max = 0;
        byte ans = 0;
        byte i;
        for (i = 0; a > 0; i++) {
            if (a % 10 >= max) {                        //Ищем крайнюю левую позицию максимальной цифры
                max = a % 10;
                ans = i;
            }
            a /= 10;
        }
        ans = (byte)(i - ans);                          //В переменной ans позиция при счете справа налево,
        return ans;                                     //поэтому отнимаем её от общей длины слова, чтобы получить ответ
    }


    /**
     * Даны две точки в пространстве (x1, y1) и (x2, y2). Вам нужно найти Y координату третьей точки (x3, y3),
     * которая находится на той же прямой что и первые две.
     */
    public static double lineFunction(int x1, int y1, int x2, int y2, int x3) {
        double k = (double) (y1 - y2) / (double) (x1 - x2);  //Находим коэффициент линейного уравнения k
        double y3 = -k * (x2 - x3) + y2;                     //Находим y3, суммируя прирост и значение y2
        return y3;
    }

    /**
     * Даны 4 точки в пространстве A(x1, y1), B(x2, y2), C(x3, y3), D(x4, y4). Найдите площадь выпуклого
     * четырехуголька ABCD.
     * Это дополнительное задание, необязательное для выполнения
     */
    public static double square(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        double sum1 = (x1 * y2) + (x2 * y3) + (x3 * y4) + (x4 * y1); //Находим сумму первой группы
        double sum2 = (y1 * x2) + (y2 * x3) + (y3 * x4) + (y4 * x1); //Находим сумму второй группы
        double ans = Math.abs(sum1 - sum2) / 2;                      //Деление их абсолютной разности на 2 даёт площадь
        return ans;
    }

}
