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
        return (long) (a * ((long) Math.pow(q, n) - 1) / (q - 1));
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
        int xSpeed = right - left;
        int ySpeed = up - down;

        if (0 >= grassX || 0 >= grassY || right >= grassX || up >= grassY)
            return 1;
        if (xSpeed <= 0 && ySpeed <= 0)
            return Integer.MAX_VALUE;

        if (xSpeed <= 0)//зная на сколько клеток вверх смещается гусеница каждый день, считаем когда она достигнет травы
            return (int) Math.ceil(1 + (grassY - up) / (double) ySpeed);
        if (ySpeed <= 0)
            return (int) Math.ceil(1 + (grassX - right) / (double) xSpeed);

        //минимум из предыдущих двух выражений в случае если гусеница по окончанию дня смещается как вправо, так и вверх
        //на положительное число клеток
        return Integer.min((int) Math.ceil(1 + (grassY - up) / (double) ySpeed), (int) Math.ceil(1 + (grassX - right) / (double) xSpeed));
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int result;

        if (order < 8)//остаток от деления на 16^(order) делим нацело на 16^(order-1) и получаем hex число на позиции order
            result = (Math.floorDiv(Math.floorMod(n, 1 << (4 * order)), 1 << (4 * (order - 1))));
        else
            result = Math.floorDiv(n, 1 << (4 * (order - 1)));

        if (result < 10)
            return (char) (result + '0');
        return (char) (result + 'A' - 10);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long min = a & 15, tmp;
        byte minIndex = 1, tmpIndex = 2;
        a >>= 4;
        while (a != 0) {
            tmp = a & 15;
            if (tmp < min) {
                min = tmp;
                minIndex = tmpIndex;
            }
            tmpIndex++;
            a >>= 4;
        }
        return minIndex;
    }

}
