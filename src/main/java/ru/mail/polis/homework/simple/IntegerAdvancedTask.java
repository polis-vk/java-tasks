package ru.mail.polis.homework.simple;


import static java.lang.Math.pow;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final double EPS = 1e-10;
    private static final int HEX = 16;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        return (q == 1) ? (long) a * n : (long) (a * (1 - pow(q, n)) / (1 - q));
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
        int x = 0;
        int y = 0;
        int days = 0;
        while (true) {
            days += 1;
            if (y + up >= grassY || x + right >= grassX) {
                break;
            }
            x += (right - left);
            y += (up - down);
            if (x <= 0 && y <= 0) {
                return Integer.MAX_VALUE;
            }
        }
        return days;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int counter = 0;
        int hexDigit = 0;
        while (counter != order) {
            hexDigit = n % HEX;
            n /= HEX;
            counter++;
        }
        return (char) ((hexDigit > 9) ? 'A' + hexDigit % 9 - 1 : '0' + hexDigit);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte result = 0;
        byte counter = 0;
        long hexDigit = Long.MAX_VALUE;
        while (a > 0) {
            counter++;
            long currHexDigit= a % HEX;
            if (currHexDigit < hexDigit) {
                hexDigit = currHexDigit;
                result = counter;
            }
            a /= HEX;
        }
        return result;
    }

}