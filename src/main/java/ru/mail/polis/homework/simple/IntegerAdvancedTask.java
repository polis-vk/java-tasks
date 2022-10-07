package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final double EPS = 1e-10;

    private static final int HEX_RADIX = 16;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (q == 1) {
            return (long) a * n;
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
        /*
         * Решение при помощи формулы суммы n-ого члена арифмитической прогрессии.
         * Формула: xn = x1 + d * (n - 1), где d = x2 - x1
         * Выражаем n из формулы выше
         */
        if (up >= grassY || right >= grassX) {
            return 1;
        }
        if (up - down <= 0 && right - left <= 0) {
            return Integer.MAX_VALUE;
        }
        double daysX = (double) (grassX - right + (right - left)) / (right - left);
        double daysY = (double) (grassY - up + (up - down)) / (up - down);
        return (int) Math.min(Math.ceil(Math.abs(daysX)), Math.ceil(Math.abs(daysY)));
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int nCopy = n;
        for (int i = 1; i < order; i++) {
            nCopy /= HEX_RADIX;
        }
        char hexValue = Character.forDigit(nCopy % HEX_RADIX, HEX_RADIX);
        if (Character.isDigit(hexValue)) {
            return hexValue;
        }
        return Character.toUpperCase(hexValue);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long aCopy = a;
        byte order = 0;
        byte minOrder = 0;
        int min = HEX_RADIX;
        while (aCopy != 0) {
            order++;
            byte temp = (byte) (aCopy % HEX_RADIX);
            if (temp < min) {
                min = temp;
                minOrder = order;
            }
            aCopy /= HEX_RADIX;
        }
        return minOrder;
    }

}
