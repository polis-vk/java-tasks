package ru.mail.polis.homework.simple;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.PI;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {
    private static final int N_CUBIC_EQUATION_ROOTS = 3;
    private static final double ERROR_IN_DOUBLES_COMPARING = 1e-6;

    /**
     * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
     * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
     * Считаем, что все три корня вещественные.
     * <p>
     * Если используете какой-то конкретный способ, напишите какой.
     * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
     */
    // Используем формулу Кардано-Виета
    public static String equation(int a, int b, int c, int d) {
        ArrayList<Double> xRoots = new ArrayList<>();
        if (hasRootThirdMultiplicity(b, c)) {
            double rootThirdMultiplicity = Math.cbrt((double) -d / a);
            return rootThirdMultiplicity + ", " + rootThirdMultiplicity + ", " + rootThirdMultiplicity;
        }
        double canonicalA = (double) b / a,
               canonicalB = (double) c / a,
               canonicalC = (double) d / a;

        initializeXRootsByNull(xRoots);
        double Q = calculateQ(canonicalA, canonicalB),
               R = calculateR(canonicalA, canonicalB, canonicalC),
               QSqrt = Math.sqrt(Q),
               phi = calculatePhi(R, Q, QSqrt);

        setXRoots(xRoots, Q, QSqrt, phi, canonicalA);
        Collections.sort(xRoots);
        return xRoots.get(2) + ", " + xRoots.get(1) + ", " + xRoots.get(0);
    }

    private static boolean hasRootThirdMultiplicity(int b, int c) {
        return b == 0 && c == 0;
    }

    private static void initializeXRootsByNull(ArrayList<Double> xRoots) {
        for (int i = 0; i < N_CUBIC_EQUATION_ROOTS; ++i) {
            xRoots.add(0.0);
        }
    }

    private static double calculateQ(double A, double B) {
        return (A * A - 3.0 * B) / 9;
    }

    private static double calculateR(double A, double B, double C) {
        return (2 * Math.pow(A, 3) - 9 * A * B + 27 * C) / 54;
    }

    private static double calculatePhi(double R, double Q, double QSqrt) {
        return Math.acos(R / (Q * QSqrt)) / 3;
    }

    private static void setXRoots(ArrayList<Double> xRoots, double Q, double QSqrt, double phi, double A) {
        xRoots.set(0, -2 * QSqrt * Math.cos(phi) - A / 3.0);
        xRoots.set(1, -2 * QSqrt * Math.cos(phi - 2 * PI / 3) - A / 3.0);
        xRoots.set(2, -2 * QSqrt * Math.cos(phi + 2 * PI / 3) - A / 3.0);
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (Math.abs(a1 - a2) > ERROR_IN_DOUBLES_COMPARING) {
            return 0;
        }
        // Прямая y = a1x + b1 проходит через точку (0;b1). Будем считать расстояние между прямыми
        // как расстояние между точкой (0;b1) и прямой y = a2x + b2 (формула для Ро)
        // Используем уже модифицированную для нашего случая формулу
        double ro = Math.abs(b2 - b1) / Math.sqrt(a2 * a2 + 1);
        return (float) ro;
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
        // Вычисляем два вектора, по которым будем строить плоскость
        int v1x = x2 - x1,
            v1y = y2 - y1,
            v1z = z2 - z1;
        int v2x = x3 - x1,
            v2y = y3 - y1,
            v2z = z3 - z1;

        // Вычисляем нормаль к плоскости как векторное произведение двух векторов, вычисленных выше
        int A = v1y * v2z - v1z * v2y,
            B = v1z * v2x - v1x * v2z,
            C = v1x * v2y - v1y * v2x;

        // Теперь плоскость имеет вид: (x-x1)A + (y-y1)B + (z-z1)C = 0, где x,y,z - координаты любой точки плоскости
        // Отсюда z4 = ((x4-x1)A + (y4-y1)B)/C + z1
        double z4 = z1 - ((x4 - x1) * A + (y4 - y1) * B) / (double) C;
        return z4;
    }
}
