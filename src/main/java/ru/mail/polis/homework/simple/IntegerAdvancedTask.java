package ru.mail.polis.homework.simple;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final double EPS = 1e-10;
    private final static int ASCII_CAPITAL_LETTERS_START_DECIMAL_VALUE = 55;
    private final static int ASCII_NUMBERS_START_DECIMAL_VALUE = 48;
    private final static int RADIX = 16;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (Math.abs(q - 1) <= EPS) {//тогда прогрессия арифметическая
            return (long) a * n;
        }
        return (long) ((a * (1 - Math.pow(q, n))) / (1 - q));
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
        if (right >= grassX || up >= grassY) {
            return 1;
        }
        int dY = up - down;
        int dX = right - left;
        if (dX <= 0 && dY <= 0) {
            return Integer.MAX_VALUE;
        }
        int daysAreNeededToReachGrassAtX = dX > 0 ? (int) Math.ceil((grassX - right) / (double) dX) + 1 : Integer.MAX_VALUE;
        int daysAreNeededToReachGrassAtY = dY > 0 ? (int) Math.ceil((grassY - up) / (double) dY) + 1 : Integer.MAX_VALUE;
        return Math.min(daysAreNeededToReachGrassAtX, daysAreNeededToReachGrassAtY);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */

    public static char kDecimal(int n, int order) {
        int numberAfterDivision = n / (int) Math.pow(RADIX, order - 1);
        int result = numberAfterDivision % RADIX;
        result += result >= 10 ? ASCII_CAPITAL_LETTERS_START_DECIMAL_VALUE : ASCII_NUMBERS_START_DECIMAL_VALUE;
        return (char) result;
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long currentValue = a;
        byte minIndex = 0;
        byte minValue = RADIX;
        byte index = 0;
        while (currentValue > 0) {
            index++;
            byte hexDigitAtCurrentIndex = (byte) (currentValue % RADIX);
            if (minValue > hexDigitAtCurrentIndex) {
                minIndex = index;
                minValue = hexDigitAtCurrentIndex;
            }
            currentValue /= RADIX;
        }
        return minIndex;
    }

}
