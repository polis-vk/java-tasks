package ru.mail.polis.homework.simple;


import java.util.ArrayList;
import java.util.Collections;
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
        return q != 1 ? (long) (a * (1 - Math.pow(q, n)) / (1 - q)) : (long) a * n;
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
        if (grassX - right <= 0 || grassY - up <= 0) {
            return 1;
        }
        if (right - left <= 0 && up - down <= 0) {
            return Integer.MAX_VALUE;
        }
        int dayOnXtoGrass = (int) Math.ceil((double) (grassX - right) / (right - left)) + 1;
        int dayOnYtoGrass = (int) Math.ceil((double) (grassY - up) / (up - down)) + 1;
        return Math.max(dayOnXtoGrass, dayOnYtoGrass);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        return convertDecimalToCharHex(n).get(order - 1);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        List<Character> arrayOfHex = convertDecimalToCharHex(a);
        return (byte) (arrayOfHex.indexOf(Collections.min(arrayOfHex)) + 1);
    }

    private static final char[] hexAlphabet = {
            '0', '1', '2', '3', '4',
            '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'
    };

    public static List<Character> convertDecimalToCharHex(long a) {
        List<Character> arrayOfHex = new ArrayList<>();
        for (long remainder = a; remainder > 0; ) {
            arrayOfHex.add(hexAlphabet[(int) (remainder % 16)]);
            remainder /= 16;
        }
        return arrayOfHex;
    }
}
