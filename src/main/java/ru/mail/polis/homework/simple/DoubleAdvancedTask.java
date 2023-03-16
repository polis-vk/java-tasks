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


        return x1 + ", " + x2 + ", " + x3;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return 0;
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
                                         int x4, int y4)
    //создаем зубчатый массив, куда записываем уравнение плоскости по 3 точкам. Матрица с этими данными
    //=0, выводим z4 из формулы. Переменные temp1,2,3 созданы для удобства
    {

        int[][] arr = new int[3][];
        arr[0] = new int[]{x2 - x1, y2 - y1, z2 - z1};
        arr[1] = new int[]{x3 - x1, y3 - y1, z3 - z1};
        arr[2] = new int[]{x4 - x1, y4 - y1};

        float temp1 = arr[1][2] * (arr[0][0] * arr[2][1] - arr[0][1] * arr[2][0]);
        int temp2 = arr[0][2] * (arr[1][0] * arr[2][1] - arr[1][1] * arr[2][0]);
        int temp3 = arr[0][0] * arr[1][1] - arr[0][1] * arr[1][0];


        double z4 = z1 + ((temp1 - temp2) / temp3);
        return z4;
    }
}
