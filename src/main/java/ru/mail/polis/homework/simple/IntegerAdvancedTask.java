package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final double EPSILON = 1E-5;

    /**
     * Сумма первых n членов геометрической прогрессии с первым элементом a и множителем q
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (Math.abs(q - 1) < EPSILON) {
            return (long) a * n;
        }
        return a * (long) (1 - Math.pow(q, n)) / (long) (1 - q);
    }

    /**
     * Гусеница ползает по столу квадратами по часовой стрелке. За день она двигается следующим образом:
     * сначала наверх на up, потом направо на right. Ночью она двигается вниз на down и налево на left.
     * Сколько суток понадобится гусенице, чтобы доползти до поля с травой?
     * Считаем, что на каждой клетке с координатами >= grassX или >= grassY находится трава
     * Если она этого никогда не сможет сделать, Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 5, 5, 20, 1) -> 2
     */
    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
        if (grassY <= 0 || grassX <= 0) {
            return 0;
        }
        if (grassY - up <= 0 || grassX - right <= 0) {
            return 1;
        }
        if (up - down <= 0 && right - left <= 0) {
            return Integer.MAX_VALUE;
        }
        if (right - left >= 0) {
            return generalSnake(right, left, grassX);
        }
        if (up - down >= 0) {
            return generalSnake(up, down, grassY);
        }
        return Math.min(
                generalSnake(up, down, grassY),
                generalSnake(right, left, grassX)
        );
    }

    private static int generalSnake(int forward, int backward, int grassMin) {
        return 1 + ceilDivision(grassMin - forward, forward - backward);
    }

    /**
     * Same as (int)Math.ceil((double) a / b)
     */
    private static int ceilDivision(int a, int b) {
        return (a + b - 1) / b;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        return hexDigit((int) ((Math.abs((long) n) >> ((order - 1) * 4)) & 0xF));
    }

    private static char hexDigit(int value) {
        if (0 <= value && value <= 9) {
            return (char) ('0' + value);
        }
        return (char) ('A' + value - 10);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long number = Math.abs(a);
        long digit = number & 0xF;
        byte order = 1;
        long minDigit = digit;
        byte minOrder = 1;
        while (number > 0) {
            digit = number & 0xF;
            if (digit < minDigit) {
                minDigit = digit;
                minOrder = order;
            }
            number >>= 4;
            order++;
        }
        return minOrder;
    }
}
