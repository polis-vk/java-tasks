package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {
    private static final double EPS = 1e-10;
    private static final int HEX = 16;
    private static final char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (Math.abs(q - 1) < EPS) {
            return (long) n * a;
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
        if (up <= down && right <= left && up < grassY && right < grassX) {
            return Integer.MAX_VALUE;
        }

        if (up >= grassY || right >= grassX) {
            return 1;
        }

        int deltaY = up - down;
        int deltaX = right - left;

        int daysX = (int) Math.ceil((double) (grassX - right) / deltaX) + 1;
        int daysY = (int) Math.ceil((double) (grassY - up) / deltaY) + 1;

        return Math.min(Math.abs(daysX), Math.abs(daysY));
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int result = HEX - 1;
        for (int i = 0; i < order; i++) {
            int remainder = n % HEX;
            result = remainder;
            n /= HEX;
        }
        return HEX_CHARS[result];
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {

        byte minIndex = 0;
        byte currentIndex = 0;
        int minValue = HEX - 1;
        while (a > 0) {
            int remainder = (int) (a % HEX);
            if (minValue > remainder) {
                minIndex = currentIndex;
                minValue = remainder;
            }

            currentIndex++;
            a /= HEX;
        }
        return (byte) (minIndex + 1);
    }
}
