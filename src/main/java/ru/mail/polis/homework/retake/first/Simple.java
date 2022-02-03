package ru.mail.polis.homework.retake.first;

/**
 * Все по 2 балла
 * <p>
 * 12 баллов
 */
public class Simple {

    /**
     * Вывести два корня квадратного уравнения через запятую: a * x ^ 2 + b * x + c = 0;
     * Вывод менять не нужно, надо только посчитать x1 и x2, где x1 > x2
     * Пример: (1, 5, 4) -> "-1.0, -4.0"
     */
    public static String equation(int a, int b, int c) {
        double x1, x2;
        double cd = 1.0 * c / a;
        double bd = 1.0 * b / a;
        double d = bd * bd - 4 * cd;
        x1 = (-bd + Math.sqrt(d)) / 2;
        x2 = (-bd - Math.sqrt(d)) / 2;
        return x1 + ", " + x2;
    }

    /**
     * Нужно посчитать расстояние, между двумя точками (x1, y1) и (x2, y2)
     * Пример: (0, 0, 3, 4) -> 5.0
     */
    public static float length(double x1, double y1, double x2, double y2) {
        return (float) Math.sqrt(Math.pow(Math.abs(x2 - x1), 2) + Math.pow(Math.abs(y2 - y1), 2));
    }

    /**
     * Сумма чисел от 1 до n (1 + 2 + 3 + ... + n)
     * Пример: (5) -> 15
     */
    public static int sum(int n) {
        return (1 + n) * n / 2;
    }

    /**
     * Гусеница поднимается по стене длиной height на высоту top за день, ночью гусеница сползает на bottom.
     * Сколько дней понадобится гусенице, чтобы доползти до потолка. Если она этого никогда не сможет сделать,
     * Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 2) -> 8
     */
    public static int snake(int height, int top, int bottom) {
        if (height > top && top - bottom <= 0) {
            return Integer.MAX_VALUE;
        }
        if (height < top) {
            return 1;
        }
        return (int) Math.ceil((double) (height - top) / (top - bottom)) + 1;
    }

    /**
     * Дано число n и номер разряда order. Выведите цифру стоящую на нужном разряде
     * Пример: (454355, 3) -> 3. Строки использовать нельзя
     */
    public static int kDecimal(int n, int order) {
        int divider = (int) Math.pow(10, order - 1);
        return Math.abs(n / divider % 10);
    }


    /**
     * Выведите факториал от числа n
     * Пример: (5) -> 120
     */
    public static long factorial(byte n) {
        long result = 1;
        for (int i = 2; i <= n; i++) {
            result = result * i;
        }
        return result;
    }
}
