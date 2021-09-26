package ru.mail.polis.homework.simple;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (q == 1.0) {
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
     * Пример: (10, 3, 5, 5, 20, 1) -> 2
     */
    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
        int xBeforeLastDay = grassX - right;
        int yBeforeLastDay = grassY - up;

        if (xBeforeLastDay <= 0 || yBeforeLastDay <= 0) {
            return 1;
        }

        int xMoveAtOneDay = right - left;
        int yMoveAtOneDay = up - down;

        if (xMoveAtOneDay <= 0 && yMoveAtOneDay <= 0) {
            return Integer.MAX_VALUE;
        }

        int xDays = (int) Math.ceil((double) xBeforeLastDay / xMoveAtOneDay) + 1;
        int yDays = (int) Math.ceil((double) yBeforeLastDay / yMoveAtOneDay) + 1;

        return Math.max(xDays, yDays);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        List<Integer> hexNumber = convertToHex(n);
        int result = hexNumber.get(order - 1);

        char charToSum = result < 10 ? '0' : 'A' - 10;

        return (char) (result + charToSum);
    }

    private static List<Integer> convertToHex(long n) {
        List<Integer> result = new ArrayList<>();
        long number = n;

        while (number > 0) {
            result.add((int) (number % 16));
            number = number / 16;
        }

        return result;
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        List<Integer> hexNumber = convertToHex(a);
        byte numberOfMinDigit = 17;
        int minDigit = 17;

        for (int i = 0; i < hexNumber.size(); i++) {
            int current = hexNumber.get(i);
            if (current < minDigit) {
                minDigit = current;
                numberOfMinDigit = (byte) (i + 1);
            }
        }
        return numberOfMinDigit;
    }
}
