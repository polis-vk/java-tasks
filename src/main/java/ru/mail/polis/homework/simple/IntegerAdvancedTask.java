package ru.mail.polis.homework.simple;


import java.util.ArrayList;

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
        if (Double.compare(q, 1d) == 0) {
            return (long) a * n;
        }
        return (long) (a * (1 - Math.pow(q, n)) / (1 - q));
    }

    /**
     * Гусеница ползает по столу квадратами по часовой стрелке. За день она двигается следующим образом:
     * сначала наверх на up, потом направо на right. Ночью она двигается вниз на down и налево на left.
     * Сколько суток понадобится гусенице, чтобы доползти до поля с травой?
     * Считаем, что на каждой клетке с координатами >= grassX или >= grassY находится трава
     * Если она этого никогда не сможет сделать, Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 5, 5, 20, 1) -> 2
     */

    private static int moveInAbstractAxis(int moveForward, int moveBack, int distance) {
        final int moveDay = moveForward - moveBack;
        if (moveDay <= 0) {
            return Integer.MAX_VALUE;
        }
        return ((distance - moveForward) + moveDay - 1) / moveDay + 1; // целочисленное округление вверх
    }

    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
        if (up >= grassY || right >= grassX) {
            return 1;
        }
        final int upDays = moveInAbstractAxis(up, down, grassY);
        final int rightDays = moveInAbstractAxis(right, left, grassX);
        return Math.min(upDays, rightDays);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */


    public static char kDecimal(int n, int order) {
        final int figure = (n / (int) Math.pow(16, order - 1)) % 16;
        return (char) (figure > 9 ? 'A' + figure - 10 : '0' + figure);
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
        byte indexMin = 1;
        byte minFigure = Byte.MAX_VALUE;
        byte i = 1;
        while (number >= 16) {
            if (number % 16 < minFigure) {
                indexMin = i;
                minFigure = (byte) (number % 16);
            }
            i++;
            number /= 16;
        }
        return number % 16 < minFigure ? i : indexMin;
    }
}
