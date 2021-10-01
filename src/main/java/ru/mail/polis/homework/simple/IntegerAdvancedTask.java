package ru.mail.polis.homework.simple;


import java.util.Locale;

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
        return (q == 1) ? ((long) a * n) : (long) (a * (Math.pow(q, n) - 1) / (q - 1));
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
        if ((grassX <= 0) || (grassY <= 0)) {
            return 0;
        }
        grassX -= right;
        grassY -= up;
        if ((grassY <= 0) || (grassX <= 0)) {
            return 1;
        }

        int xTime = Integer.MAX_VALUE;
        int yTime = Integer.MAX_VALUE;
        int dX = right - left;
        int dY = up - down;
        if (dX > 0) {
            xTime = (int) Math.ceil((double) grassX / dX) + 1;
        }
        if (dY > 0) {
            yTime = (int) Math.ceil((double) grassY / dY) + 1;
        }
        return Math.min(xTime, yTime);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        if (order < 1) {
            throw new IllegalArgumentException("Order must be > 0");
        }
        return (Integer.toHexString((int) ((n / (Math.pow(16, (order - 1)))) % 16))).toUpperCase().toCharArray()[0];
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        if (a < 0) {
            throw new IllegalArgumentException("Digital must be > 0");
        }
        long min = a % 16;
        byte counter = 1;
        byte firstMinimum = counter;
        while (a > 0) {
            if (min > (a % 16)) {
                firstMinimum = counter;
                min = a % 16;
            }
            a /= 16;
            counter++;
        }
        return firstMinimum;
    }

}
