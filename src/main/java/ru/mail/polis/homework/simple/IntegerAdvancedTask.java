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
     *
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        long result = 0;
        for (int i = 0; i < n; ++i) {
            result += a * Math.pow(q, i);
        }
        return result;
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
        int result = 1;
        int xSnake = 0;
        int ySnake = 0;

        if (up <= down && up < grassY && right <= left && right < grassX) {
            return Integer.MAX_VALUE;
        }

        while (true) {
            xSnake += right;
            ySnake += up;

            if (xSnake >= grassX || ySnake >= grassY) {
                return result;
            }

            xSnake -= left;
            ySnake -= down;

            ++result;
        }
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int result = 0;
        int temp = n;

        for (int i = 0; i < order; ++i) {
            result = temp % 16;
            temp = temp / 16;
        }

        if (result <= 9) {
            return (char) ('0' + result);
        } else {
            return (char) ('A' + result - 10);
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
        int result = 1;
        long min = 15;
        long temp = a;

        for (int i = 1; temp != 0; ++i) {
            if (temp % 16 < min) {
                result = i;
                min = temp % 16;
            }

            temp = temp / 16;
        }

        return (byte) result;
    }

}
