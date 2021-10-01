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
    private static final double ERROR_IN_DOUBLES_COMPARING = 1e-6;
    private static final int HEX_NUMERIC_SYSTEM = 16;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем q
     * a + aq + aq^2 + ... + aq^(n-1)
     *
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (Math.abs(q - 1) <= ERROR_IN_DOUBLES_COMPARING) {
            return n * a;
        }
        return (long) (a * (Math.pow(q, n) - 1) / (q - 1));
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
        if (right >= grassX || up >= grassY) {
            return 1;
        }

        // Змейка доползёт до клетки grassX за ceil(((double) (grassX - left)) / deltaX) дней,
        // а до клетки grassY - за ceil(((double) (grassY - down)) / deltaY) дней.
        // Из этих величин ответом будет наименьшая.
        int nX = deltaX <= 0 ? Integer.MAX_VALUE : (int) Math.ceil(((double) (grassX - left)) / deltaX);
        int nY = deltaY <= 0 ? Integer.MAX_VALUE : (int) Math.ceil(((double) (grassY - down)) / deltaY);
        return nX > nY ? nY : nX;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     **/

    public static char kDecimal(int n, int order) {
        for (int numberOrder = 1; numberOrder < order; ++numberOrder) {
            n /= HEX_NUMERIC_SYSTEM;
        }
        if (n % HEX_NUMERIC_SYSTEM <= 9) {
            return (char) ('0' + n % HEX_NUMERIC_SYSTEM);
        }
        return (char) ('A' + (n % HEX_NUMERIC_SYSTEM - 10));
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte curIndex = 1;
        byte minDigitIndex = -1;
        byte minDigit = HEX_NUMERIC_SYSTEM;
        long num = a;
        while (num > 0) {
            if (minDigit == 0) {
                break;
            }
            if (num % HEX_NUMERIC_SYSTEM < minDigit) {
                minDigit = (byte) (num % HEX_NUMERIC_SYSTEM);
                minDigitIndex = curIndex;
            }
            ++curIndex;
            num /= HEX_NUMERIC_SYSTEM;
        }
        return minDigitIndex;
    }
}
