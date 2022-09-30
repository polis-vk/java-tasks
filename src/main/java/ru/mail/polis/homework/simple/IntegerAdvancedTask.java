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
        long sum = 0;
        for (int i = 0; i < n; i++) {
            sum = (long) (sum + a * Math.pow(q, i));
        }
        return sum;
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
        int result = 0;
        int counter = 1;
        if (stepForward >= end) {
            return counter;
        } else if (stepBack >= stepForward) {
            return Integer.MAX_VALUE;
        } else {
            while (result < end) {
                result = result + stepForward;
                if (crossEnd(result, end)) {
                    break;
                }
                result = result - stepBack;
                counter++;
            }
        }
        return counter;
    }

    public static boolean crossEnd(int resultXorY, int point) {
        return (resultXorY >= point);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int number = n;
        int counter = 1;
        int numberInOrder;
        char numberInOrderHex;
        while (counter < order) {
            number = number / 16;
            counter++;
        }
        numberInOrder = number % 16;
        if (numberInOrder <= 9) {
            numberInOrderHex = (char) ('0' + numberInOrder);
        } else {
            numberInOrderHex = (char) ('A' + numberInOrder - 10);
        }
        return numberInOrderHex;
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
        long numberInHex;
        char digitNumber = 'G';
        int counterMinDigitNumber = 0;
        int counterGeneral = 0;
        while (number > 0) {
            numberInHex = number % 16;
            counterGeneral++;
            if (numberInHex <= 9) {
                if ((char) ('0' + numberInHex) < digitNumber) {
                    digitNumber = (char) ('0' + numberInHex);
                    counterMinDigitNumber++;
                    if (counterMinDigitNumber < counterGeneral) {
                        counterMinDigitNumber = counterMinDigitNumber + (counterGeneral - counterMinDigitNumber);
                    }
                }
            } else {
                if ((char) ('0' + numberInHex) < digitNumber) {
                    digitNumber = (char) ('A' + numberInHex - 10);
                    counterMinDigitNumber++;
                    if (counterMinDigitNumber < counterGeneral) {
                        counterMinDigitNumber = counterMinDigitNumber + (counterGeneral - counterMinDigitNumber);
                    }
                }
            }
            number = number / 16;
        }
        return (byte) counterMinDigitNumber;
    }

}
