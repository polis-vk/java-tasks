package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final double EPS = 1e-10;
    private static final int HEXDECIMAL = 16;

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
        return Math.min(oneDay(up, down, grassY), oneDay(right, left, grassX));
    }

    public static int oneDay(int stepForward, int stepBack, int end) {
        if (stepForward >= end) {
            return 1;
        } else if (stepBack >= stepForward) {
            return Integer.MAX_VALUE;
        } else {
            return (int) (Math.ceil((double) (end - stepForward) / (stepForward - stepBack)) + 1);
        }
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int number = n;
        int numberInOrder;
        number = (int) (number / Math.pow(HEXDECIMAL, order - 1));
        numberInOrder = number % HEXDECIMAL;
        if (numberInOrder <= 9) {
            return (char) ('0' + numberInOrder);
        } else {
            return (char) ('A' + numberInOrder - 10);
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
        long number = a;
        int counter = 1;
        int minDigitNumber = 1;
        long minNumber = (int) ('F');
        while (number > 0) {
            if (number % HEXDECIMAL < minNumber) {
                minNumber = number % HEXDECIMAL;
                minDigitNumber = counter;
            }
            counter++;
            number = number / HEXDECIMAL;
        }
        return (byte) minDigitNumber;
    }

}
