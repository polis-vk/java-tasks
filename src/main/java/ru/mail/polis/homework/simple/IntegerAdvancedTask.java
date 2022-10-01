package ru.mail.polis.homework.simple;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final double EPS = 1e-10;
    private static final byte HEX = 16;
    private static final byte INT_TO_CHAR_NUM = 48;
    private static final byte INT_TO_CHAR_LETTER = 55;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (q > 1 + EPS || q < 1 - EPS) {
            return (long) (a * (Math.pow(q, n) - 1) / (q - 1));
        } else {
            return a * n;
        }
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
        if ((up >= grassY) || (right >= grassX)) {
            return 1;
        }
        int dailyX = right - left;
        int dailyY = up - down;
        int daysX = 0;
        int daysY = 0;
        if (dailyX > 0) {
            daysX = (grassX - right - 1) / dailyX + 2;
        }
        if (dailyY > 0) {
            daysY = (grassY - up - 1) / dailyY + 2;
        }
        if (daysX == 0 && daysY == 0) {
            return Integer.MAX_VALUE;
        } else if (daysX == 0 || (daysY != 0 && daysY <= daysX)) {
            return daysY;
        } else {
            return daysX;
        }
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int result = (n / (int) Math.pow(HEX, order - 1) % HEX);
        if (result < 10) {
            return (char) (result + INT_TO_CHAR_NUM); // 0..9
        } else {
            return (char) (result + INT_TO_CHAR_LETTER); // A..F
        }
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte minOrder = 1;
        byte minDigit = HEX;
        long num = a;
        byte order = 1;
        while (num > 0) {
            byte currentDigit = (byte) (num % HEX);
            if (currentDigit < minDigit) {
                minDigit = currentDigit;
                minOrder = order;
            }
            order++;
            num /= HEX;
        }
        return minOrder;
    }

}
