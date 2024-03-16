package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

//    private static final double EPS = 1e-10;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (q == 1) {
            return (long) n * a;
        } else {
            long s = (long) ((a * (Math.pow(q, n) - 1)) / (q - 1));
            return s;
        }
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
        int x = 0;
        int y = 0;
        if (right >= grassX || up >= grassY
                || right - left > 0 || up - down > 0) {
            for (int i = 1; ; i++) {
                x = x + right;
                y = y + up;
                if (x >= grassX || y >= grassY) {
                    return i;
                }
                x = x - left;
                y = y - down;
            }
        } else {
            return Integer.MAX_VALUE;
        }
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int hexadecimal;
        for (int i = 1; n % 16 != 0; i++) {
            hexadecimal = n % 16;
            if (i == order) {
                return toHex(hexadecimal);
            }
            n = n / 16;
        }
        return toHex(n / 16);
    }

    private static char toHex(int hexadecimal) {
        if (hexadecimal == 10) {
            return 'A';
        } else if (hexadecimal == 11) {
            return 'B';
        } else if (hexadecimal == 12) {
            return 'C';
        } else if (hexadecimal == 13) {
            return 'D';
        } else if (hexadecimal == 14) {
            return 'E';
        } else if (hexadecimal == 15) {
            return 'F';
        } else {
            return (char) (hexadecimal + '0');
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
        byte count = 1;
        long min = a % 16;
        a = a / 16;
        byte i = 2;
        for (; a / 16 != 0; i++) {
            if (min == 0) break;
            if (a % 16 < min) {
                min = a % 16;
                count = i;
            }
            a = a / 16;
        }
        if (a % 16 < min && a % 16 != 0) {
            count = i;
        }

        return count;
    }

}
