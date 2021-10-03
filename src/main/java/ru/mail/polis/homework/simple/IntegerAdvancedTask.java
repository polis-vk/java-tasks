package ru.mail.polis.homework.simple;


import java.util.Locale;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {
    public static final double EPSILON = 1e-6;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        return Math.abs(q - 1.0) < EPSILON ? (long) a * n : (long) (a * (1 - Math.pow(q, n)) / (1 - q));
    }

    /**
     * Гусеница ползает по столку квадратами по часовой стрелке. За день она двигается следующим образом:
     * сначала наверх на up, потом направо на right. Ночью она двигается вниз на down и налево на left.
     * Сколько суток понадобится гусенице, чтобы доползти до правого верхнего поля с травой?
     * Если она этого никогда не сможет сделать, Верните число Integer.MAX_VALUE;
     * Считаем, что на каждой клетке с координатами >= grassX или >= grassY находится трава
     * Пример: (10, 3, 5, 5, 20, 11) -> 2
     */
    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
        if (right >= grassX || up >= grassY) {
            return 1;
        }
        if (right - left <= 0 && up - down <= 0) {
            return Integer.MAX_VALUE;
        }
        int x = 0;
        int y = 0;
        int i = 0;
        while (x < grassX && y < grassY) {
            i++;
            x += right;
            y += up;
            if (x >= grassX || y >= grassY) {
                return i;
            }
            x -= left;
            y -= down;
        }
        return i;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int current = n;
        int residual = 0;
        for (int i = 0; i < order; i++) {
            residual = current % 16;
            current >>= 4;
        }
        if (residual > 9) {
            return (char) (residual + ('A' - 10));
        }
        return (char) (residual + '0');
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        if (a == 0) {
            return 1;
        }
        long currentDecimal = a;
        byte minNumber = 16;
        byte residue;
        byte minIndex = 0;
        byte i = 1;
        while (currentDecimal > 0) {
            residue = (byte) (currentDecimal % 16);
            if (residue < minNumber) {
                if (residue == 0) {
                    return i;
                }
                minNumber = residue;
                minIndex = i;
            }
            currentDecimal >>= 4;
            i++;
        }
        return minIndex;
    }
}
