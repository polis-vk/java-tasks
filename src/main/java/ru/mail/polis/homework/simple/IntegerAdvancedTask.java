package ru.mail.polis.homework.simple;


import java.util.InputMismatchException;

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
        long result = 0;
        double tempQ = 1;
        for (int i = 0; i < n; i++) {
            result += a * tempQ;
            tempQ = tempQ * q;
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
        if (grassX <= right || grassY <= up) {
            return 1;
        }
        if (right - left <= 0 && up - down <= 0) {
            return Integer.MAX_VALUE;
        }
        int daysHorrisonyally = 0;
        int daysVertically = 0;
        if (right - left > 0) {
            daysHorrisonyally = (int) Math.ceil((double) (grassX - right) / (right - left)) + 1;
        } else {
            daysHorrisonyally = Integer.MAX_VALUE;
        }
        if (up - down > 0) {
            daysVertically = (int) Math.ceil((double) (grassY - up) / (up - down)) + 1;
        } else {
            daysVertically = Integer.MAX_VALUE;
        }
        return Math.min(daysHorrisonyally, daysVertically);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        final int discharge = 16;
        final char[] hexadecimal = {
                '0', '1', '2', '3',
                '4', '5', '6', '7',
                '8', '9', 'A', 'B',
                'C', 'D', 'E', 'F'};
        int result = 0;
        int tempN = n;
        for (int i = 0; i < order; i++) {
            result = tempN % discharge;
            tempN = tempN / discharge;
        }
        return hexadecimal[result];
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long tempA = a;
        byte min = Byte.MAX_VALUE;
        byte index = 0;
        final int discharge = 16;
        for (int i = 1; tempA > 0; i++) {
            if (tempA % discharge < min) {
                min = (byte) (tempA % discharge);
                index = (byte) i;
            }
            tempA = tempA / discharge;
        }
        return index;
    }

}
