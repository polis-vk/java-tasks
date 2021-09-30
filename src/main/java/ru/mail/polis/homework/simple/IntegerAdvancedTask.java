package ru.mail.polis.homework.simple;


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
        if (q == 0.0) {
            return a;
        } else if (q == 1.0) {
            return (long) a * n;
        } else {
            return (long) (a * (1 - Math.pow(q, n)) / (1 - q));
        }
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
        if (up < grassY && up - down <= 0 && right < grassX && right - left <= 0) {
            return Integer.MAX_VALUE;
        } else {
            int y = (grassY - up);
            int x = (grassX - right);
            if (x <= 0 || y <= 0) {
                return 1;
            }
            y = y / (up - down) + 1;
            x = x / (right - left) + 1;
            y += y % (up - down) == 0 ? 0 : 1;
            x += x % (right - left) == 0 ? 0 : 1;
            y = y > 0 ? y : Integer.MAX_VALUE;
            x = x > 0 ? x : Integer.MAX_VALUE;
            return Math.min(x, y);
        }
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        String hexString = Integer.toHexString(n).toUpperCase();
        return hexString.charAt(hexString.length() - order);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        String hexString = Long.toHexString(a);
        char min = Character.MAX_VALUE;
        int offset = 0;
        for (int i = hexString.length() - 1; i >= 0; i--) {
            if (hexString.charAt(i) < min) {
                min = hexString.charAt(i);
                offset = hexString.length() - i;
            }
        }
        return (byte) offset;
    }

}
