package ru.mail.polis.homework.simple;


import java.util.Locale;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    /**
     * Сумма элементов геометрической прогрессии с 1 элементом a и множителем r (a + aq + aq^2 + ... + aq^n)
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        return (long) (a * (1 - Math.pow(q, n)) / (1 - q));
    }

    /**
     * Гусеница ползает по столку квадратами по часовой стрелке. За день она двигается следующим образом:
     * сначала наверх на up, потом направо на right. Ночью она двигается вниз на down и налево на left.
     * Сколько суток понадобится гусенице, чтобы доползти до правого верхнего поля с травой?
     * Если она этого никогда не сможет сделать, Верните число Integer.MAX_VALUE;
     * Считаем, что на каждой клетке с координатами >= grassX и >= grassY находится трава
     * Пример: (10, 3, 5, 5, 11, 20) -> 2
     */
    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
        if (up >= grassY || right >= grassX) {
            return 1;
        }
        if (down >= up && left >= right) {
            return Integer.MAX_VALUE;
        }
        int step1 = up - down;
        int step2 = right - left;
        int heightBeforeLastClimb = grassY - up;
        int widthBeforeLastClimb = grassX - right;
        int daysUp = heightBeforeLastClimb % step1 == 0 ? heightBeforeLastClimb / step1 + 1 : heightBeforeLastClimb / step1 + 2;
        int daysRight = widthBeforeLastClimb % step2 == 0 ? widthBeforeLastClimb / step2 + 1 : widthBeforeLastClimb / step2 + 2;
        return Integer.max(daysUp, daysRight);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        String nStr = Integer.toHexString(n).toUpperCase(Locale.ROOT);
        return nStr.charAt(nStr.length() - order);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        String aStr = Long.toHexString(a);
        int minNumber = aStr.chars().min().getAsInt();
        return (byte) (aStr.length() - aStr.lastIndexOf(Character.toString(minNumber)));
    }

}
