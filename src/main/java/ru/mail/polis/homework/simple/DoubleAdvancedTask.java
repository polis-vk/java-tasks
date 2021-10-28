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
        // Если два из коэффициентов a, b, c и коэффициент d равны нулю, то уравнение имеет один корень - 0
        if ((d == 0) && ((a == 0 ? 1 : 0) + (b == 0 ? 1 : 0) + (c == 0 ? 1 : 0) == 2)) {
            return 0d + ", " + 0d + ", " + 0d;
        }

        // Ищем все делители коэффициентов a и d
        List<Integer> aDividers = calculateDividers(a);
        List<Integer> dDividers = calculateDividers(d);

        // Сюда будем складывать корни уравнения
        HashSet<Double> roots = new HashSet<>();

        // При d = 0 рассматриваем ситуацию иначе
        if (d == 0) {
            // Корни могут быть одинаковыми, поэтому хэш-сэт не подходит
            List<Double> dRoots = new ArrayList<>();

            // Изначально при d = 0 имеем корень, равный 0
            dRoots.add(0d);

            // При c = 0 получаем ещё один корень, равный 0, и другой отличный от нуля корень
            if (c == 0) {
                dRoots.add(0d);
                dRoots.add((double) -b / a);
            } else {
                // Иначе имеем квадратное уравнение, которое и решаем
                dRoots.addAll(solveQuadraticEquation(new double[]{a, b, c}));
            }
            // Сортируем наши корни по неубыванию
            dRoots.sort((o1, o2) -> (int) ((o2 - o1) * 1000));

            // Возвращаем строку с корнями
            return prettifyString(dRoots);
        }

        // Рассматриваем основной случай с d != 0
        // Ищем все легко вычисляемые корни уравнения
        for (Integer p : dDividers) {
            for (Integer q : aDividers) {
                // pq - p / q, p - один из делителей d, а q - один из делителей a
                // Это всё исходит из свойства корней степенного уравнения
                double pq = ((double) p / q);

                if (checkIfItIsDivider(a, b, c, d, pq)) {
                    roots.add(pq);
                }

                if (checkIfItIsDivider(a, b, c, d, -pq)) {
                    roots.add(-pq);
                }

                if (checkIfItIsDivider(a, b, c, d, p)) {
                    roots.add((double) p);
                }

                if (checkIfItIsDivider(a, b, c, d, -p)) {
                    roots.add((double) -p);
                }
            }
        }

        // Если все корни уравнения легко вычисляемы, то возвращаем их
        if (roots.size() == 3) {
            // В компараторе идёт умножение на тысячу для того, чтобы уменьшить вероятность совпадения при целочисленном
            // делении
            TreeSet<Double> values = new TreeSet<>((o1, o2) -> (int) ((o2 - o1) * 1000));
            values.addAll(roots);
            return prettifyString(values);
        }

        // Если корень только один, то имеет смысл по схеме Горнера высчитать квадратное уравнение и решить его
        if (roots.size() == 1) {
            //
            double root = (double) roots.toArray()[0];

            // Схема Горнера
            double[] cubicExpression = {a, b, c, d};
            double[] quadraticExpression = new double[3];
            quadraticExpression[0] = cubicExpression[0];
            for (int i = 1; i < quadraticExpression.length; i++) {
                quadraticExpression[i] = root * quadraticExpression[i - 1] + cubicExpression[i];
            }

            // Решаем полученное квадратное уравнение
            roots.addAll(solveQuadraticEquation(quadraticExpression));

            TreeSet<Double> values = new TreeSet<>((o1, o2) -> (int) ((o2 - o1) * 1000));
            values.addAll(roots);
            return prettifyString(values);
        }

        return 0d + ", " + 0d + ", " + 0d;
    }

    // Возвращает нужную строку по методу toString коллекции
    private static String prettifyString(Collection<Double> collection) {
        return collection.toString().replace("[", "").replace("]", "");
    }

    // Метод, решающий квадратное уравнение
    // Он получает массив с коэффициентами квадратного уравнения
    private static List<Double> solveQuadraticEquation(double[] quadraticExpression) {
        List<Double> results = new ArrayList<>();

        double a = quadraticExpression[0];
        double b = quadraticExpression[1];
        double c = quadraticExpression[2];

        // Рассматриваем разные случаи квадратного уравнения и находим решение
        if (a == 0 && b != 0 && c != 0) {
            results.add(-c / b);
        } else if (a != 0 && b == 0 && c != 0) {
            results.add(Math.sqrt(-c / a));
            results.add(-Math.sqrt(-c / a));
        } else if (a != 0 && b != 0 && c == 0) {
            results.add(0d);
            results.add(-b / a);
        } else if (a != 0 && b != 0 && c != 0) {
            double D = b * b - 4 * a * c;
            if (D == 0) {
                results.add(-b / (2 * a));
            } else if (D > 0) {
                results.add((-b - Math.sqrt(D)) / (2 * a));
                results.add((-b + Math.sqrt(D)) / (2 * a));
            }
        } else if ((a != 0 && b == 0 && c == 0)) {
            results.add(0d);
            results.add(0d);
        } else if (a == 0 && b != 0 && c == 0) {
            results.add(0d);
        }

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
        // f(x) = a1 * x - b1 и g(x) = a2 * x - b2
        // Если коэффициенты перед x равны нулю, то функции представляют собой прямые вида y = b
        if (a1 == 0 && a2 == 0) {
            return (float) Math.abs(b1 - b2);
        }

        // Если a1 = a2, то прямые параллельны
        // Если b1 = b2, то прямые пересекаются в точке на оси OX
        if (a1 != a2 || b1 == b2) return 0;

        // Нужно найти h
        // Попробуем решить через синус прямоугольного треугольника
        float h;

        // Определяем функции с минимальным и максимальным значениями b
        double minB = Math.min(b1, b2);
        double maxB = Math.max(b1, b2);

        // Определяем точки пересечения прямых и оси OX
        double x1 = Math.min(-b1 / a1, -b2 / a2);
        double x2 = Math.max(-b1 / a1, -b2 / a2);

        // a, b, c (гипотенуза) - стороны прямоугольного треугольника
        double a;

        // Если функции убывают, то высчитываем "a" через функцию с минимальным b, иначе - с максимальным b
        if (a1 < 0) {
            a = a1 * x1 + maxB;
        } else {
            a = a1 * x2 + minB;
        }

        // b - расстояние между точками пересечения прямых и оси OX
        double b = x2 - x1;

        // Вычисляем "c" по теореме пифагора
        double c = Math.sqrt(a * a + b * b);

        // Через соотношение синуса в прямоугольном треугольнике найдём h
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
        // Найдём значение z4 через уравнение по трём точкам
        // Вид уравнения плоскости: Ax - By + Cz + D = 0
        // Мы можем подставить вместо x и y данные значения x4 и y4 (далее - x0 и y0, так же и искомое z0)
        // Уравнение примет вид: Ax0 - Bx0 + Cz + D = 0
        // D образуется при полном раскрытии скобок, но это не наш выбор
        // Просто упростим выражение и выразим z: z = (-Ax0 + Bx0) / C
        // Все значения легко выражаются с помощью метода решения матрицей
        // z может быть выражена как z0 - z1
        // Вид уравнения: z0 = ((-Ax0 + Bx0) / C) + z1
        double z0;

        // Следующие выражения получены из матрицы
        int Ax0 = (x4 - x1) * ((y1 - y2) * (z2 - z3) - (y2 - y3) * (z1 - z2));
        int By0 = (y4 - y1) * ((x1 - x2) * (z2 - z3) - (x2 - x3) * (z1 - z2));
        int C = (x1 - x2) * (y2 - y3) - (x2 - x3) * (y1 - y2);

        z0 = ((double) (-Ax0 + By0) / C) + z1;

        return z0;
    }
}
