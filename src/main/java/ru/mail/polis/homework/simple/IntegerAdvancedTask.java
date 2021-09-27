package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    /**
     * Сумма первых n членов геометрической прогрессии с первым элементом a и множителем q
     * a + aq + aq^2 + ... + aq^(n-1)
     *
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        final double EPSILON = 1E-5;

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
        return Math.min(snakeOneDimensional(up, down, grassY), snakeOneDimensional(right, left, grassX));
    }

    /**
     * Сколько суток понадобится гусенице, чтобы доползти до клетки с травой, если она начинает в клетке 0 и
     *
     * @param right    клеток ползет гусеница днем в положительном направлении
     * @param left     клеток ползет гусеница ночью в отрицательном направлении
     * @param grassMin координата клетки, начиная с которой растет трава
     * @return количество суток
     */
    private static int snakeOneDimensional(int right, int left, int grassMin) {
        if (grassMin <= 0) {
            return 0;
        }

        if (right - left > 0) {
            return 1 + (int) Math.ceil((double) (grassMin - right) / (right - left));
        }

        if (grassMin - right <= 0) {
            return 1;
        }

        return Integer.MAX_VALUE;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        if (n == Integer.MIN_VALUE) {
            return 'F';
        }
        return "0123456789ABCDEF".charAt((int) ((Math.abs((long) n) >> ((order - 1) * 4)) & 0xF));
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        if (a == Long.MIN_VALUE) {
            return 1;
        }
        a = Math.abs(a);
        long minDigit = a & 0xF;
        int minOrder = 0;
        for (int i = 0; a > 0; i++) {
            if ((a & 0xF) < minDigit) {
                minDigit = a & 0xF;
                minOrder = i;
            }
            a >>= 4;
        }
        return (byte) (minOrder + 1);
    }

}
