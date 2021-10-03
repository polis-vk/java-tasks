package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final double EPS = 1E-6;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (Math.abs(q - 1.0) < EPS) {
            return (long) a * n;
        }
        return (long) (a * (1 - Math.pow(q, n)) / (1 - q));
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
        if (up >= grassY || right >= grassX) {
            return 1;
        }
        int incYPerDay = up - down;
        int incXPerDay = right - left;
        if (incXPerDay <= 0 && incYPerDay <= 0) {
            return Integer.MAX_VALUE;
        }
        double needToY = Math.ceil((double) grassY / incYPerDay) - Math.floor((double) up / incYPerDay) + 1;
        double needToX = Math.ceil((double) grassX / incXPerDay) - Math.floor((double) right / incXPerDay) + 1;
        return minPositive((int) needToX, (int) needToY);
    }

    private static int minPositive(int first, int second) {
        if (first >= 0) {
            if (second < 0) {
                return first;
            }
            return Math.min(first, second);
        }
        return second;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        return Character.toUpperCase(Character.forDigit(getDigit(n, order), 16));
    }

    private static byte getDigit(long n, int order) {
        long bitwiseComputeDigit = (order == 1) ? 15 : 15L << (order - 1) * 4;
        return (byte) ((bitwiseComputeDigit & n) >> (order - 1) * 4);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long number = a;
        byte minDigit = 15;
        byte indexOfMinDigit = 0;
        byte order = 1;
        while (number != 0) {
            byte digit = getDigit(a, order);
            if (digit < minDigit) {
                if (digit == 0) {
                    return order;
                }
                minDigit = digit;
                indexOfMinDigit = order;
            }
            order++;
            number /= 16;
        }
        return indexOfMinDigit;
    }

}
