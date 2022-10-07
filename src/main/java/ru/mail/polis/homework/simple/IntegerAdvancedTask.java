package ru.mail.polis.homework.simple;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

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
        if (equalsDoubles(q, 1, EPS)) {
            return (long) n * a;
        } else {
            return (long) ((a * (1 - Math.pow(q, n))) / (1 - q));
        }
    }

    private static boolean equalsDoubles(double a, double b, double eps) {
        return Math.abs(a - b) <= eps;
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
        if (up >= grassY || right >= grassX) { //краевой случай - гусеница доберется до травы за 1 день
            return 1;
        } else if (down >= up && left >= right) { //краевой случай - гусеница никогда не доберется до травы
            return Integer.MAX_VALUE;
        } else {
            double deltaX = Math.abs(left - right);
            double deltaY = Math.abs(up - down);

            double answerX = (grassX - right) / deltaX;
            double answerY = (grassY - up) / deltaY;

            //в последний день может так случится, что гусенице не придется возвращаться
            if (answerX * deltaX + right >= grassX) {
                answerX++;
            }
            if (answerY * deltaY + up >= grassY) {
                answerY++;
            }

            return answerX < answerY ? (int) Math.ceil(answerX) : (int) Math.ceil(answerY);
        }
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        char[] hexDigits = Integer.toHexString(n).toCharArray();
        return Character.toUpperCase(hexDigits[hexDigits.length - order]);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        char[] hexDigits = Long.toHexString(a).toCharArray();
        char minDigit = hexDigits[hexDigits.length - 1];
        int number = 1;
        for (int i = hexDigits.length - 1; i >= 0; i--) {
            if (Character.toUpperCase(hexDigits[i]) < Character.toUpperCase(minDigit)) {
                minDigit = hexDigits[i];
                number = hexDigits.length - i;
            }
        }
        return (byte) number;
    }

}
