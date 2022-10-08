package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final int HEXADECIMAL_BASE = 16;
    private static final double EPS = 1e-10;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (Math.abs(q - 1) <= EPS) {
            return (long) a * n;
        }
        return (long) (a * (Math.pow(q, n) - 1) / (q - 1));
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
        if (right >= grassX || up >= grassY) {
            return 1;
        }
        return Math.min(numOfDays(grassY, up, down), numOfDays(grassX, right, left));
    }

    private static int numOfDays(int grass, int daytimeStep, int nightStep) {
        int speed = daytimeStep - nightStep;
        if (speed <= 0) {
            return Integer.MAX_VALUE;
        }
        return (int) Math.ceil((double) (grass - nightStep) / speed);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int number = n / (int) Math.pow(HEXADECIMAL_BASE, order - 1) % HEXADECIMAL_BASE;
        if (number < 10) {
            return (char) (number + '0');
        }
        return (char) ('A' + number - 10);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        /*
         * первая цифра 1-ого номера минимальная по умолчанию
         * перебирать начинаем со 2-ого номера
         */
        long min = a % HEXADECIMAL_BASE;
        long varA = a / HEXADECIMAL_BASE;
        byte count = 2;
        byte res = 1;
        while (varA > 0) {
            if (varA % HEXADECIMAL_BASE < min) {
                min = varA % HEXADECIMAL_BASE;
                res = count;
            }
            varA /= HEXADECIMAL_BASE;
            count++;
        }
        return res;
    }
}
