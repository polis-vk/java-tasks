package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final double EPS = 1e-10;
    private static final int HEX_BASE = 16;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        return (long) (Math.abs(q - 1.0) < EPS ? n * a : a * (Math.pow(q, n) - 1) / (q - 1));
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
        if ((up >= grassY) || (right >= grassX)) {
            return 1;
        }
        int deltaY = up - down;
        int deltaX = right - left;
        if ((deltaY <= 0) && (deltaX <= 0)) {
            return Integer.MAX_VALUE;
        }
        int dayX = roundedUp(grassX - right, deltaX) + 1;
        int dayY = roundedUp(grassY - up, deltaY) + 1;
        return dayX > 0 && dayY > 0 ? Math.min(dayX, dayY) : (dayX > 0 ? dayX : dayY);
    }

    private static int roundedUp(int number, int div) {
        return number / div + (number % div == 0 ? 0 : 1);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру, стоящую на нужном разряде, для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        return Character.toUpperCase(Character.forDigit(n / (int) Math.pow(HEX_BASE, order - 1) % HEX_BASE, HEX_BASE));
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long number = a;
        int min = HEX_BASE;
        int index = 0;
        int rest;
        for (int i = 1; number > 0; i++, number /= HEX_BASE) {
            rest = (int) (number % HEX_BASE);
            if (rest < min) {
                min = rest;
                index = i;
            }
            if (min == 0) {
                break;
            }
        }
        return (byte) index;
    }

}
