package ru.mail.polis.homework.simple;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {
    private static final double EPS = 1e-10;

    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */

    // Интерфейс математической функции
    public interface FuncInterface{

        // Значение функции в точке x
        double f(double x);

        // Значение производной от функции в точке х
        double df(double x);
    }

    // Класс математической функции кубического полинома
    public static class CubePolynomial implements FuncInterface{

        // Коэффициенты кубического полинома
        double a, b, c, d;

        // Кубический полином определяют коэффициенты
        CubePolynomial(double a, double b, double c, double d){
            this.a = a;
            this.b = b;
            this.c = c;
            this.d = d;
        }

        // Значение полинома в точке х
        public double f(double x){
            return a * Math.pow(x, 3) + b * Math.pow(x, 2) + c * x + d;
        }

        // Значение производной от полинома в точке х
        public double df(double x){
            return a * 3 * Math.pow(x, 2) + b * 2 * x + c;
        }
    }

    // Метод Ньютона
    static double methodNewton(FuncInterface Func, double X0) {
        // Первая итерация
        double x0 = X0;
        double x1 = x0 - Func.f(x0) / Func.df(x0);

        // Пока |x_n - x_n-1| > Eps - продолжаем вычислять по схеме:
        // x_n = x_n-1 - f(x_n-1) / df(x_n-1)
        while (Math.abs(x1 - x0) > EPS / 2) {
            x0 = x1;
            x1 = x0 - Func.f(x0) / Func.df(x0);
        }


        // Если x = NaN, то x = 0
        if (Double.isNaN(x1)) {
            x1 = 0;
        }

        return x1;
    }

    public static String equation(int a, int b, int c, int d) {

        // Создаем экземпляр кубического полинома
        CubePolynomial polynomCubic = new CubePolynomial(a,b,c,d);

        // Задаем три произвольные начальные точки
        double a1 = 10;
        double a2 = 0;
        double a3 = -10;


        double x1 = methodNewton(polynomCubic, a1);
        double x2 = methodNewton(polynomCubic, a2);
        double x3 = methodNewton(polynomCubic, a3);
        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (Math.abs(a1 - a2) > EPS) {
            return 0;
        }

        return (float) (Math.abs(b1 - b2) / Math.sqrt(a1 * a2 + 1));
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
        // Пусть A = {x1, y1, z1}, B = {x2, y2, z2}, C = {x3, y3, z3}, D = {x4, y4, z4}

        // |x4 - x1  y4 - y1  z4 - z1|
        // |x2 - x1  y2 - y1  z2 - z1| == 0  => A, B, C, D - лежат в одной плоскости
        // |x3 - x1  y3 - y1  z3 - z1|

        // Находим определители миноров
        int mx41 = (y2 - y1) * (z3 - z1) - (z2 - z1) * (y3 - y1);
        int my41 = (x2 - x1) * (z3 - z1) - (z2 - z1) * (x3 - x1);
        int mz41 = (x2 - x1) * (y3 - y1) - (y2 - y1) * (x3 - x1);

        // Находим z4
        return ((double) (-(x4 - x1) * mx41 + (y4 - y1) * my41) / mz41) + z1;
    }
}
