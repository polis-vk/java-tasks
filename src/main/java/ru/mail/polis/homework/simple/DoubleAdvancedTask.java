package ru.mail.polis.homework.simple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        //Решал с помощью тригонометрической формулы Виета
        double aC = (double) b / a;
        double bC = (double) c / a;
        double cC = (double) d / a;
        double q = (Math.pow(aC, 2) - 3 * bC) / 9;
        double r = (2 * Math.pow(aC, 3) - 9 * aC * bC + 27 * cC) / 54;
        double s = Math.pow(q, 3) - Math.pow(r, 2);
        double f = (Math.acos(r / Math.pow(q, 1.5))) / 3;
        List<Double> arrayOfRoot = new ArrayList<>();
        if (s > 0) {
            arrayOfRoot.add(-2 * Math.sqrt(q) * Math.cos(f) - aC / 3);
            arrayOfRoot.add(-2 * Math.sqrt(q) * Math.cos(f + 2 * Math.PI / 3.0) - aC / 3);
            arrayOfRoot.add(-2 * Math.sqrt(q) * Math.cos(f - 2 * Math.PI / 3.0) - aC / 3);
        } else {
            arrayOfRoot.add(-2 * Math.cbrt(r) - aC / 3);
            arrayOfRoot.add(Math.cbrt(r) - aC / 3);
            arrayOfRoot.add(Math.cbrt(r) - aC / 3);
        }
        Collections.sort(arrayOfRoot);
        return arrayOfRoot.get(2) + ", " + arrayOfRoot.get(1) + ", " + arrayOfRoot.get(0);
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        return a1 != a2 ? 0 : (float) (Math.abs(b1 - b2) / Math.sqrt(a1 * a2 + 1));
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

        double a = y1 * (z2 - z3) + y2 * (z3 - z1) + y3 * (z1 - z2);
        double b = z1 * (x2 - x3) + z2 * (x3 - x1) + z3 * (x1 - x2);
        double c = x1 * (y2 - y3) + x2 * (y3 - y1) + x3 * (y1 - y2);
        double d = -(x1 * (y2 * z3 - y3 * z2) + x2 * (y3 * z1 - y1 * z3) + x3 * (y1 * z2 - y2 * z1));

        return -(a * x4 + b * y4 + d) / c;
    }
}