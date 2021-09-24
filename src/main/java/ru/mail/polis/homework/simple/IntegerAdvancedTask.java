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
        long result = a;
        for (int i = 1; i < n; i++) {
            result += a * Math.pow(q, i);
        }
        return result;
    }


    /**
     * Гусеница ползает по столку квадратами по часовой стрелке. За день она двигается следующим образом:
     * сначала наверх на up, потом направо на right. Ночью она двигается вниз на down и налево на left.
     * Сколько суток понадобится гусенице, чтобы доползти до правого верхнего поля с травой?
     * Если она этого никогда не сможет сделать, Верните число Integer.MAX_VALUE;
     * Считаем, что на каждой клетке с координатами >= grassX или >= grassY находится трава
     * Пример: (10, 3, 5, 5, 11, 20) -> 2
     */

    private static int moveInAbstractAxis(int moveForward, int moveBack, int distance) {
        final int moveDay = moveForward - moveBack;
        if (moveForward >= distance) {
            return 1;
        } else if (moveDay <= 0) {
            return Integer.MAX_VALUE;
        }
        return ((distance - moveForward) + moveDay - 1) / moveDay + 1; // целочисленное округление вверх
    }

    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
        final int upDays = moveInAbstractAxis(up, down, grassY);
        final int rightDays = moveInAbstractAxis(right, left, grassX);
        return Math.min(upDays, rightDays);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Пример: (454355, 2) -> D
     */

    private static ArrayList<Character> ReverseDecToHex(long n) {
        ArrayList<Character> result = new ArrayList<>();
        long oneFigure;
        while (n >= 16) {
            oneFigure = n % 16;
            result.add((char) (oneFigure > 9 ? 'A' + oneFigure - 10 : '0' + oneFigure));
            n /= 16;
        }
        oneFigure = n % 16;
        result.add((char) (oneFigure > 9 ? 'A' + oneFigure - 10 : '0' + oneFigure));
        return result;
    }


    public static char kDecimal(int n, int order) {
        return ReverseDecToHex(n).get(order - 1);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        final ArrayList<Character> reverseHex = ReverseDecToHex(a);
        char min = Character.MAX_VALUE;
        byte minIndex = 1;
        for (int i = 0; i < reverseHex.size(); i++) {
            if (reverseHex.get(i) < min) {
                min = reverseHex.get(i);
                minIndex = (byte) (i + 1);
            }
        }
        return minIndex;
    }
}
