package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final double EPS = 1e-10;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        double progressionSum = 0;
        double currentPowedQ = 1;
        for (int i = 0; i < n; i++) {
            progressionSum += a * currentPowedQ;
            currentPowedQ *= q;
        }
        return (long) progressionSum;
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
        int minDaysCountToGrassY = Integer.MAX_VALUE;
        if (grassY - up <= 0) {
            minDaysCountToGrassY = 1;
        } else if (up - down > 0) {
            minDaysCountToGrassY = 1 + (int) Math.ceil((double) (grassY - up) / (up - down));
        }
        int minDaysCountToGrassX = Integer.MAX_VALUE;
        if (grassX - right <= 0) {
            minDaysCountToGrassX = 1;
        } else if (right - left > 0) {
            minDaysCountToGrassX = 1 + (int) Math.ceil((double) (grassX - right) / (right - left));
        }
        return Math.min(minDaysCountToGrassX, minDaysCountToGrassY);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        for (int i = 0; i < order - 1; i++) {
            n /= 16;
        }
        byte orderValue = (byte) (n % 16);
        if (orderValue < 10) {
            return (char) ('0' + orderValue);
        }
        return (char) ('A' - 10 + orderValue);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte minValue = (byte) (a % 16);
        a /= 16;
        byte minValueNumber = 1;
        byte valueNumber = 2;
        while (a != 0) {
            if (a % 16 < minValue) {
                minValue = (byte) (a % 16);
                minValueNumber = valueNumber;
            }
            a /= 16;
            valueNumber++;
        }
        return minValueNumber;
    }

}
