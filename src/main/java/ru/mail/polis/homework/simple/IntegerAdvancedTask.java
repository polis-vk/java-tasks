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
        if (q != 1) {
            return (long) (a * (Math.pow(q, n) - 1) / (q - 1));
        }
        else {
            return (long) a * n;
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
        int grassShiftedX = grassX - right; // Корректировка координат - если гусеница достигнет цели,
        int grassShiftedY = grassY - up; // то ночное сползание не учитывается.
        if ((0 >= grassShiftedY) || (0 >= grassShiftedX)) {
            return 1;
        }
        if ((down >= up) && (left >= right)) {
            return Integer.MAX_VALUE;
        }
        int deltaUp = up - down;
        int deltaRight = right - left;
        if (deltaUp <= 0) {
            return (grassShiftedX + deltaRight - 1) / // Здесь и далее применяется деление с округлением вверх
                    deltaRight + 1; // "+ 1" является компенсацией за финальные "неполные" сутки
        }
        else if (deltaRight <= 0) {
            return (grassShiftedY + deltaUp - 1) / deltaUp + 1; // Аналогично
        }
        else {
            return Math.min((grassShiftedX + deltaRight - 1) / deltaRight + 1,
                    (grassShiftedY + deltaUp - 1) / deltaUp + 1);
        }
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        return Integer.toHexString((int) (Math.floor(n) /
                Math.pow(16, order - 1)) % 16).toUpperCase().charAt(0);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long inp = a;
        long minDigit = Integer.MAX_VALUE;
        byte position = 1;
        byte i = 1;
        while (inp != 0) {
            long digit = inp % 16;
            inp = inp / 16;
            if (minDigit > digit) {
                minDigit = digit;
                position = i;
            }
            i++;
        }
        return position;
    }

}
