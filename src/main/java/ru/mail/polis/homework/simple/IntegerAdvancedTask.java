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
        if (q == 1) {
            return (long) a * n;
        }
        return (long) ((a * (Math.pow(q, n) - 1)) / (q - 1));
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
        if (up > grassX || right > grassY) {
            return 1;
        }
        if (down >= up && left >= right) {
            return Integer.MAX_VALUE;
        }
        int vertical = linearMoveTime(grassY, up, down);
        int horizontal = linearMoveTime(grassX, right, left);
        if (vertical * horizontal < 0) {
            return Math.max(vertical, horizontal);
        }
        return Math.min(vertical, horizontal);
    }

    public static int linearMoveTime(int length, int forward, int back) {
        return (int) Math.ceil((double) (length - forward) / (forward - back)) + 1;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int number = ((n / (int) Math.pow(16, order - 1)) % 16);
        if (number >= 10) {
            return (char) (number + 'A' - 10);
        }
        return (char) (number + '0');
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte minDigit = Byte.MAX_VALUE;
        byte digitPlace = 0;
        byte minDigitPlace = -1;
        byte currentDigit;
        long number = a;

        while (number > 0 && minDigit > 0) {
            currentDigit = (byte) (number % 16);
            digitPlace++;
            number /= 16;
            if (currentDigit < minDigit) {
                minDigit = currentDigit;
                minDigitPlace = digitPlace;
            }
        }
        return minDigitPlace;
    }

}
