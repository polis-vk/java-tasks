package ru.mail.polis.homework.simple;


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
        if (Math.abs(q) < EPS) {
            return a;
        }
        if (Math.abs(1.0 - q) < EPS) {
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
    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
        if (up >= grassY || right >= grassX) {
            return 1;
        }
        if (up - down <= 0 && right - left <= 0) {
            return Integer.MAX_VALUE;
        }
        int y = (grassY - up);
        int x = (grassX - right);
        y = y / (up - down) + 1;
        x = x / (right - left) + 1;
        y += y % (up - down) == 0 ? 0 : 1;
        x += x % (right - left) == 0 ? 0 : 1;
        y = y > 0 ? y : Integer.MAX_VALUE;
        x = x > 0 ? x : Integer.MAX_VALUE;
        return Math.min(x, y);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int temp = n;
        for (int i = 0; i < order - 1; i++) {
            temp /= 16;
        }
        temp %= 16;
        if (temp >= 10) {
            return (char) ('A' + temp - 10);
        }
        return (char) ('0' + temp);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long temp = a;
        long min = Long.MAX_VALUE;
        byte minIndex = 1;
        byte currIndex = 1;
        while (temp != 0 && min != 0) {
            if (temp % 16 < min) {
                min = temp % 16;
                minIndex = currIndex;
            }
            temp /= 16;
            currIndex++;
        }
        return minIndex;
    }

}
