package ru.mail.polis.homework.simple;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {

    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */
    public static String equation(int a, int b, int c, int d) {
        double x1 = 0;
        double x2 = 0;
        double x3 = 0;
        //Вычисления проводим по формуле Кардано
        double p = ((double)(3*a*c - b*b)) / (3*a*a);
        double q = ((double)(2*b*b*b-9*a*b*c+27*a*a*d)) / (27*a*a*a);
        double disq = Math.pow(q/2, 2) + Math.pow(p/3, 3);
        double y1, y2, y3, phi;
        if(q < 0) {
        phi = Math.atan(Math.sqrt((-1)*disq)/((-1) * q/2));
        }
        else if(q > 0) {
            phi = Math.atan(Math.sqrt((-1)*disq)/((-1) * q/2)) + Math.PI;
        }
        else {
            phi = Math.PI / 2;
        }
        y1 = 2 * Math.sqrt((-1)*p/3) * Math.cos(phi/3);
        y2 = 2 * Math.sqrt((-1)*p/3) * Math.cos(phi/3 + 2*Math.PI/3);
        y3 = 2 * Math.sqrt((-1)*p/3) * Math.cos(phi/3+ 4*Math.PI/3);
        x3 = Math.round(y3 - b / (3*a));
        x1 = (y1 - b / (3.0*a));
        x2 = (y2 - b / (3.0*a));
        x3 = (y3 - b / (3.0*a));
        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if(a1 != a2) {
            return 0;
        }
        return (float) (Math.abs(b2 - b1)/(Math.sqrt(a1*a1 + 1)));
    }

    /**
     * Даны три точки в пространстве (x1, y1, z1) , (x2, y2, z2) и (x3, y3, z3). Вам нужно найти Z координату
     * четвертой точки (x4, y4, z4), которая находится на той же плоскости что и первые три.
     * (0, 0, 1,
     * 1, 1, 1,
     * 10, 100, 1,
     * 235, -5) -> 1
     */
    public static double surfaceFunction(int x1, int y1, int z1,
                                         int x2, int y2, int z2,
                                         int x3, int y3, int z3,
                                         int x4, int y4) {
        //Считаем координаты векторов M1M2 и М1М3
        int vectorM1M2_X = (x2 - x1);
        int vectorM1M2_Y = (y2 - y1);
        int vectorM1M2_Z = (z2 - z1);
        int vectorM1M3_X = (x3 - x1);
        int vectorM1M3_Y = (y3 - y1);
        int vectorM1M3_Z = (z3 - z1);
        //Высчитываем через определитель матрицы коэффициенты a, b, c для уравнения плоскости
        int a = vectorM1M2_Y * vectorM1M3_Z - vectorM1M2_Z * vectorM1M3_Y;
        int b = vectorM1M2_Z * vectorM1M3_X - vectorM1M2_X * vectorM1M3_Z;
        int c = vectorM1M2_X * vectorM1M3_Y - vectorM1M2_Y * vectorM1M3_X;
        //Находим коэффициент d
        int d = (-1) * ((a * x1) + (b * y1) + (c * z1));
        //Зная уравнение плоскости, подставляем две точки в уравнение и находим третью точку
        int z4 = ((-1) * ((a * x4) + (b * y4) + d)) / c;
        return z4;
    }
}
