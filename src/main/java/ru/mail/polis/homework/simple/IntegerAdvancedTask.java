package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        double numerator = a * (Math.pow(q, n) - 1);
        double denominator = q - 1;
        return (long) (numerator / denominator);
    }

    /**
     * Гусеница ползает по столку квадратами по часовой стрелке. За день она двигается следующим образом:
     * сначала наверх на up, потом направо на right. Ночью она двигается вниз на down и налево на left.
     * Сколько суток понадобится гусенице, чтобы доползти до правого верхнего поля с травой?
     * Если она этого никогда не сможет сделать, Верните число Integer.MAX_VALUE;
     * Считаем, что на каждой клетке с координатами >= grassX или >= grassY находится трава
     * Пример: (10, 3, 5, 5, 11, 20) -> 2
     */
    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
        int xMoveAtOneDay = right - left;
        int yMoveAtOneDay = up - down;

        int xBeforeLastDay = grassX - right;
        int yBeforeLastDay = grassY - up;

        if (xBeforeLastDay <= 0 || yBeforeLastDay <= 0) {
            return 1;
        }

        if (xMoveAtOneDay <= 0 && yMoveAtOneDay <= 0) {
            return Integer.MAX_VALUE;
        }

        int xDays = (int) Math.ceil((double) xBeforeLastDay / xMoveAtOneDay) + 1;
        int yDays = (int) Math.ceil((double) yBeforeLastDay / yMoveAtOneDay) + 1;

        return Math.max(xDays, yDays);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        String hexNumber = Integer.toHexString(n).toUpperCase();
        return hexNumber.charAt(hexNumber.length() - order);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        String hexNumber = Long.toHexString(a);
        byte numberOfMinDigit = 17;
        char minDigit = 'f';

        for (int i = hexNumber.length() - 1; i >= 0; i--) {
            char current = hexNumber.charAt(i);
            if (current < minDigit) {
                minDigit = current;
                numberOfMinDigit = (byte) (hexNumber.length() - i);
            }
        }
        return numberOfMinDigit;
    }

}
