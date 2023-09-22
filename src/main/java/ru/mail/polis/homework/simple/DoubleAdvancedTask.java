package ru.mail.polis.homework.simple;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

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
        if ((d == 0) && ((a == 0 ? 1 : 0) + (b == 0 ? 1 : 0) + (c == 0 ? 1 : 0) == 2)) {
            return 0d + ", " + 0d + ", " + 0d;
        }

        List<Integer> aDividers = calculateDividers(a);
        List<Integer> dDividers = calculateDividers(d);
        HashSet<Double> roots = new HashSet<>();

        if (d == 0) {
            List<Double> dRoots = new ArrayList<>();
            dRoots.add(0d);
            if (c == 0) {
                dRoots.add(0d);
                dRoots.add((double) -b / a);
            } else {dRoots.addAll(solveQuadraticEquation(new double[]{a, b, c}));}
            dRoots.sort((o1, o2) -> (int) ((o2 - o1) * 1000));
            return prettifyString(dRoots);
        }

        for (Integer p : dDividers) {
            for (Integer q : aDividers) {
                double pq = ((double) p / q);
                if (checkIfItIsDivider(a, b, c, d, pq)) roots.add(pq);
                if (checkIfItIsDivider(a, b, c, d, -pq)) roots.add(-pq);
                if (checkIfItIsDivider(a, b, c, d, p)) roots.add((double) p);
                if (checkIfItIsDivider(a, b, c, d, -p)) roots.add((double) -p);
            }
        }

        if (roots.size() == 3) {
            TreeSet<Double> values = new TreeSet<>((o1, o2) -> (int) ((o2 - o1) * 1000));
            values.addAll(roots);
            return prettifyString(values);
        }

        if (roots.size() == 1) {
            double root = (double) roots.toArray()[0];
            double[] cubicExpression = {a, b, c, d};
            double[] quadraticExpression = new double[3];
            quadraticExpression[0] = cubicExpression[0];
            for (int i = 1; i < quadraticExpression.length; i++) {
                quadraticExpression[i] = root * quadraticExpression[i - 1] + cubicExpression[i];
            }
            roots.addAll(solveQuadraticEquation(quadraticExpression));
            TreeSet<Double> values = new TreeSet<>((o1, o2) -> (int) ((o2 - o1) * 1000));
            values.addAll(roots);
            return prettifyString(values);
        }
        return 0d + ", " + 0d + ", " + 0d;
    }
    private static String prettifyString(Collection<Double> collection) {
        return collection.toString().replace("[", "").replace("]", "");
    }

    private static List<Double> solveQuadraticEquation(double[] quadraticExpression) {
        List<Double> results = new ArrayList<>();

        double a = quadraticExpression[0];
        double b = quadraticExpression[1];
        double c = quadraticExpression[2];
        double x1, x2;
        double d = Math.sqrt(Math.pow(b,2)-4*a*c);
        x1 = ((-1)*b + d) / 2.0/a;
        x2 = ((-1)*b - d) / 2.0/a;
        results.add(x1); results.add(x2);
        return results;
    }

    // Метод, проверяющий, является ли данное значение корнем уравнения
    private static boolean checkIfItIsDivider(float a, float b, float c, float d, double n) {
        return a * n * n * n + b * n * n + c * n + d == 0;
    }

    // Метод, находящий делители числа
    private static List<Integer> calculateDividers(int a) {
        List<Integer> dividers = new ArrayList<>();
        dividers.add(1);
        dividers.add(-1);
        if (a != 1) {
            dividers.add(a);
            dividers.add(-a);
        }
        int sqrt = (int) Math.sqrt(a);
        int d = 2;
        while (d <= sqrt) {
            if (a % d == 0) {
                dividers.add(d);
                dividers.add(-d);
                dividers.add(a / d);
                dividers.add(-a / d);
            }
            d++;
        }
        dividers.sort(Comparator.comparingInt(o -> o));
        return dividers;
    }

    /**
     * Нужно посчитать расстояние, между двумя прямыми
     * Примеры: (1, 1, 2, -1) -> 0
     * (0, 1, 0, 5) -> 4
     */
    public static float length(double a1, double b1, double a2, double b2) {
        if (a1 == 0 && a2 == 0) {
            return (float) Math.abs(b1 - b2);
        }

        if (a1 != a2 || b1 == b2) return 0;
        float h;
        double minB = Math.min(b1, b2);
        double maxB = Math.max(b1, b2);
        double x1 = Math.min(-b1 / a1, -b2 / a2);
        double x2 = Math.max(-b1 / a1, -b2 / a2);
        double a;
        if (a1 < 0) a = a1 * x1 + maxB;
        else a = a1 * x2 + minB;

        double b = x2 - x1;
        double c = Math.sqrt(a * a + b * b);
        h = (float) (a * b / c);
        return h;
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
        double z0;
        int Ax0 = (x4 - x1) * ((y1 - y2) * (z2 - z3) - (y2 - y3) * (z1 - z2));
        int By0 = (y4 - y1) * ((x1 - x2) * (z2 - z3) - (x2 - x3) * (z1 - z2));
        int C = (x1 - x2) * (y2 - y3) - (x2 - x3) * (y1 - y2);
        z0 = ((double) (-Ax0 + By0) / C) + z1;

        return z0;
    }
}