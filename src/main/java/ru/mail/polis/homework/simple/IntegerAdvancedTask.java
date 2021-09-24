package ru.mail.polis.homework.simple;

import java.util.Collections;
import java.util.LinkedList;

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
    public static long progression(int a, double q, int n) {
        long res = 0;
        for (int i = 0; i < n; i++) {
            res += a * Math.pow(q, i);
        }
        return res;
    }

    /**
     * Гусеница ползает по столку квадратами по часовой стрелке. За день она двигается следующим образом:
     * сначала наверх на up, потом направо на right. Ночью она двигается вниз на down и налево на left.
     * Сколько суток понадобится гусенице, чтобы доползти до правого верхнего поля с травой?
     * Если она этого никогда не сможет сделать, Верните число Integer.MAX_VALUE;
     * Считаем, что на каждой клетке с координатами >= grassX или >= grassY находится трава
     * Пример: (10, 3, 5, 5, 11, 20) -> 2
     */
    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
        int x = 0;
        int y = 0;
        int daysCount = 0;

        if ((up <= down) && (up < grassY) && (right <= left) && (right < grassX)) {
            return Integer.MAX_VALUE;
        }

        while ((x < grassX) && (y < grassY)) {
            y += up;
            x += right;
            daysCount++;

            if ((x >= grassX) || (y >= grassY)) {
                break;
            }

            y -= down;
            x -= left;
        }

        return daysCount;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        LinkedList<Integer> numbers = new LinkedList<>();
        while (n >= 16) {
            numbers.add(n % 16);
            n /= 16;
        }
        numbers.add(n);

        return Character.toUpperCase(Character.forDigit(numbers.get(order - 1), 16));
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        LinkedList<Long> numbers = new LinkedList<>();
        while (a >= 16) {
            numbers.add(a % 16);
            a /= 16;
        }
        numbers.add(a);

        return (byte) (numbers.indexOf(Collections.min(numbers)) + 1);
    }
}
