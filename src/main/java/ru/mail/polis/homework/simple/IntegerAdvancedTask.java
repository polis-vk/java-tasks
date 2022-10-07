package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    final static double EPS = 0.000000001d;
    final static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F'};
    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (Math.abs(q - 1) < IntegerAdvancedTask.EPS) {
            return (long) n * a;
        }
        return (long) (a * (1 - Math.pow(q, n)) / (1 - q));
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
        if (up <= down && right <= left && up < grassY && right < grassX) {
            return Integer.MAX_VALUE;
        }

        if (up > grassY || right > grassX) {
            return 1;
        }

        int daysUp = (int) (Math.ceil((double) (grassY - up) / (up - down)) + 1);
        if (daysUp < 0) {
            daysUp = Integer.MAX_VALUE;
        }
        int daysRight = (int) (Math.ceil((double) (grassX - right) / (right - left)) + 1);
        if (daysRight < 0) {
            daysRight = Integer.MAX_VALUE;
        }
        return Math.min(daysUp, daysRight);

    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        return IntegerAdvancedTask.hexDigits[(n / (int) Math.pow(16, order - 1) % 16)];
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long rest;
        long min = a % 16;
        byte pos = 0;
        byte minPos = 0;
        long num = a;
        while (num != 0) {
            rest = num % 16;
            if (rest < min) {
                min = rest;
                minPos = pos;
            }
            num = num / 16;
            pos++;
        }
        return (byte) (minPos + 1);
    }

}
