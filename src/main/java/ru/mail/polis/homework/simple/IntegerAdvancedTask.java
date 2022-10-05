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
        long result = 0;
        for (int i = 0; i < n; i++) {
            result += a;
            a *= q;
        }
        return result;
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
        if (up - down <= 0 && right - left <= 0
                && up < grassY && right < grassX) {
            return Integer.MAX_VALUE;
        }
        int days = 0;
        int snakeX = 0;
        int snakeY = 0;
        while (true) {
            snakeY += up;
            snakeX += right;
            days++;
            if (snakeX >= grassX || snakeY >= grassY) {
                break;
            }
            snakeY -= down;
            snakeX -= left;
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
        for (int i = 1; i < order; i++) {
            n /= 16;
        }
        char hexValue = Character.forDigit(n % 16, HEX_RADIX);
        if (Character.isDigit(hexValue)) {
            return hexValue;
        } else {
            return Character.toUpperCase(hexValue);
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
        byte order = 0;
        byte minOrder = 0;
        int min = Integer.MAX_VALUE;
        while (a != 0) {
            order++;
            byte temp = (byte) (a % 16);
            if (temp < min) {
                min = temp;
                minOrder = order;
            }
            a /= 16;
        }
        return minOrder;
    }
}
