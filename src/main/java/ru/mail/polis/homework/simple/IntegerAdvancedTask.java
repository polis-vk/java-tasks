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
        if (Math.abs(q - 1.0) < EPS) {
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
        } else if (left >= right && down >= up) {
            return Integer.MAX_VALUE;
        } else if (left >= right) {
            return calcSnakeTime(down, up, grassY);
        } else if (down >= up) {
            return calcSnakeTime(left, right, grassX);
        }

        return Math.min(calcSnakeTime(down, up, grassY), calcSnakeTime(left, right, grassX));
    }

    private static int calcSnakeTime(int deltaNight, int deltaDay, int grassCoord) {
        if (deltaNight >= deltaDay) {
            return -1;
        }

        int speed = deltaDay - deltaNight;
        return (int) Math.ceil((double) (grassCoord - deltaDay) / speed) + 1;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int inputNum = n;
        int digitNumber = 0;

        do {
            digitNumber++;

            byte digit = (byte) (inputNum % 16);
            if (order == digitNumber) {
                if (digit >= 10) {
                    return (char) ('A' + (digit - 10));
                } else {
                    return (char) ('0' + digit);
                }
            }

            inputNum /= 16;
        } while (inputNum > 0);

        return ' ';
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long inputNum = a;
        byte minDigit = 16;
        byte minDigitNumber = 0;
        byte digitNumber = 0;

        do {
            digitNumber++;

            byte digit = (byte) (inputNum % 16);
            if (digit < minDigit) {
                minDigit = digit;
                minDigitNumber = digitNumber;
            }

            inputNum /= 16;
        } while (inputNum > 0);

        return minDigitNumber;
    }

}
