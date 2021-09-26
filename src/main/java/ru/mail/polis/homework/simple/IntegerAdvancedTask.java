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
        if (Math.abs(q - 1) < 1e-10) {
            return (long) a * n;
        }
        return (long) ((a * (1 - Math.pow(q, n))) / (1 - q));
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
        if (up >= grassY || right >= grassX) {
            return 1;
        }

        if (up - down <= 0 && right - left <= 0) {
            return Integer.MAX_VALUE;
        }

        int xDays = (int) Math.ceil((double) (grassX - right) / (right - left));
        int yDays = (int) Math.ceil((double) (grassY - up) / (up - down));
        return (xDays >= 0 && yDays >= 0 ? Integer.min(xDays, yDays) : Integer.max(xDays, yDays)) + 1;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        char[] symbols = {'A', 'B', 'C', 'D', 'E', 'F'};
        for (int i = 0; i < order - 1; i++) {
            n /= 16;
        }
        n %= 16;
        return n < 10 ? (char) (n + '0') : symbols[n % 10];
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte idx = 0;
        long min = 16;
        for (byte i = 1; a > 0; i++) {
            long ost = a % 16;
            if (ost == 0) {
                return i;
            } else if (ost < min) {
                min = ost;
                idx = i;
            }
            a /= 16;
        }
        return idx;
    }
}

