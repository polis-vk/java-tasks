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
     *
     * Пример: (1, 2, 3) -> 7
     */

    private static final double EPS = 0.0000001;

    public static long progression(int a, double q, int n) {
        return Math.abs(q - 1) > EPS ? (long) (a * (Math.pow(q, n) - 1) / (q - 1))
                : (long) a * n;
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
        if (grassX <= right || grassY <= up) {
            return 1;
        }

        int differenceX = right - left;
        int differenceY = up - down;

        if (differenceX <= 0 && differenceY <= 0) {
            return Integer.MAX_VALUE;
        }
        return Math.min(countOfDays(grassX, differenceX, right), countOfDays(grassY, differenceY, up));
    }

    private static int countOfDays(int length, int difference, int addInAfternoon) {
        if (difference <= 0) {
            return Integer.MAX_VALUE;
        }
        if (length % difference == 0) {
            return length / difference - addInAfternoon / difference + 1;
        }
        return length / difference - addInAfternoon / difference + 2;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */

    private static final int HEXADECIMAL_BASE = 16;

    public static char kDecimal(int n, int order) {
        int truncatedNumber = Math.abs(n) / (int) Math.pow(HEXADECIMAL_BASE, order - 1);
        int lastDigitInHex = truncatedNumber % HEXADECIMAL_BASE;

        return Character.toUpperCase(Character.forDigit(lastDigitInHex, HEXADECIMAL_BASE));
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long number = a;
        byte minDigit = (byte) HEXADECIMAL_BASE;
        byte indexOfMinDigit = 0;
        byte currentDigit;

        for (byte i = 0; number > 0; i++) {
            currentDigit = (byte) (number % HEXADECIMAL_BASE);
            if (minDigit > currentDigit) {
                indexOfMinDigit = i;
                minDigit = currentDigit;
            }
            if (minDigit == 0) {
                return (byte) (indexOfMinDigit + 1);
            }

            number /= HEXADECIMAL_BASE;
        }
        return (byte) (indexOfMinDigit + 1);
    }

}
