package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final int NUMERAL_SYSTEM = 16;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        return q == 1 ? n * a : (long) ((a * Math.pow(q, n) - a) / (q - 1));
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
        double daysX = right - left <= 0
                ? Integer.MAX_VALUE
                : Math.ceil((double) (grassX - right) / (right - left)) + 1;
        double daysY = up - down <= 0
                ? Integer.MAX_VALUE
                : Math.ceil((double) (grassY - up) / (up - down)) + 1;
        return (int) Math.min(daysX, daysY);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int out = n;
        for (int i = 1; i < order; i++) {
            out /= NUMERAL_SYSTEM;
        }
        out %= NUMERAL_SYSTEM;
        return (char) (out < 10 ? '0' + out : 'A' + out - 10);
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
        int minNumber = NUMERAL_SYSTEM;
        int currentOrder = 1;
        int orderOfMinNumber = currentOrder;
        do {
            if (currentValue % NUMERAL_SYSTEM < minNumber) {
                minNumber = (int) (currentValue % NUMERAL_SYSTEM);
                orderOfMinNumber = currentOrder;
                if (minNumber == 0) {
                    return (byte) orderOfMinNumber;
                }
            }
            currentValue /= NUMERAL_SYSTEM;
            currentOrder++;
        } while (currentValue > 0);
        return (byte) orderOfMinNumber;
    }

}
