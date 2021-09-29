package ru.mail.polis.homework.simple;

import java.util.HashMap;
import java.util.Map;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем q
     * a + aq + aq^2 + ... + aq^(n-1)
     *
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (q == 1) {
            return n * a;
        }
        long sum = (long) (a * (Math.pow(q, n) - 1) / (q - 1));
        return sum;
    }

    /**
     * Гусеница ползает по столу квадратами по часовой стрелке. За день она двигается следующим образом:
     * сначала наверх на up, потом направо на right. Ночью она двигается вниз на down и налево на left.
     * Сколько суток понадобится гусенице, чтобы доползти до поля с травой?
     * Считаем, что на каждой клетке с координатами >= grassX или >= grassY находится трава
     * Если она этого никогда не сможет сделать, Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 5, 5, 20, 11) -> 2
     */
    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
        int deltaX = right - left,
            deltaY = up - down;
        if (deltaX <= 0 && deltaY <= 0) {
            if (!canReachGrassForOneDay(up, right, grassX, grassY)) {
                return Integer.MAX_VALUE;
            }
            return 1;
        }

        return countDaysForReachingGrass(deltaX, deltaY, up, right, down, left, grassX, grassY);
    }

    private static boolean canReachGrassForOneDay(int up, int right, int grassX, int grassY) {
        return right >= grassX || up >= grassY;
    }

    private static int countDaysForReachingGrass(int deltaX, int deltaY, int up, int right, int down,
        int left, int grassX, int grassY) {
        // Змейка доползёт до клетки grassX за ceil(((double) (grassX - left)) / deltaX) дней,
        // а до клетки grassY - за ceil(((double) (grassY - down)) / deltaY) дней.
        // Из этих величин ответом будет наименьшая.
        if (deltaX <= 0) {
            return (int) Math.ceil(((double) (grassY - down)) / deltaY);
        } else if (deltaY <= 0) {
            return (int) Math.ceil(((double) (grassX - left)) / deltaX);
        }

        int nX = (int) Math.ceil(((double) (grassX - left)) / deltaX),
            nY = (int) Math.ceil(((double) (grassY - down)) / deltaY);
        return nX > nY ? nY : nX;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     **/

    private static HashMap<Integer, Character> hex = new HashMap<Integer, Character>() {
        {
            put(1, '1');
            put(2, '2');
            put(3, '3');
            put(4, '4');
            put(5, '5');
            put(6, '6');
            put(7, '7');
            put(8, '8');
            put(9, '9');
            put(10, 'A');
            put(11, 'B');
            put(12, 'C');
            put(13, 'D');
            put(14, 'E');
            put(15, 'F');
        }
    };

    private static final int HEX_NUMERIC_SYSTEM = 16;

    public static char kDecimal(int n, int order) {
        for (int number_order = 1; number_order <= order - 1; ++number_order) {
            n /= HEX_NUMERIC_SYSTEM;
        }
        return hex.get(n % HEX_NUMERIC_SYSTEM);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        // Вычисляем log16(a) - количество разрядов в 16-ричном представлении
        // у числа a
        int cur_index = 1,
            min_digit_index = -1,
            min_digit = HEX_NUMERIC_SYSTEM;
        while (a > 0) {
            if (a % HEX_NUMERIC_SYSTEM < min_digit) {
                min_digit = (int) (a % HEX_NUMERIC_SYSTEM);
                min_digit_index = cur_index;
            }
            ++cur_index;
            a /= HEX_NUMERIC_SYSTEM;
        }
        return (byte) min_digit_index;
    }
}
