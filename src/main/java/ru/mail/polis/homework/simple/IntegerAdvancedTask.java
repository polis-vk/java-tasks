package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final double EPS = 1e-10;
    private static final byte BASE = 16;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (Math.abs(q - 1) < EPS) {
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
        if (right >= grassX || up >= grassY) {
            return 1;
        }
        if (left >= right && down >= up) {
            return Integer.MAX_VALUE;
        }
        int movesX = (int) Math.ceil((double) (grassX - right) / (right - left)) + 1;
        int movesY = (int) Math.ceil((double) (grassY - up) / (up - down)) + 1;
        if (movesX > 0 && movesY > 0) {
            return Math.min(movesX, movesY);
        }
        return Math.max(movesX, movesY);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int digit = (int) (n / Math.pow(BASE, order - 1) % BASE);
        if (digit < 10) {
            return (char) (digit + '0');
        }
        return (char) (digit - 10 + 'A');
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte digit = (byte) (a % BASE);
        byte min = digit;
        byte pos = 1;
        byte minPos = 1;
        long num = a / BASE;
        while (num > 0 && min != 0) {
            digit = (byte) (num % BASE);
            pos++;
            if (digit < min) {
                min = digit;
                minPos = pos;
            }
            num /= BASE;
        }
        return minPos;
    }

}
