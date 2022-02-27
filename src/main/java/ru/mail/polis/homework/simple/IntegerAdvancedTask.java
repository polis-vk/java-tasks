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
     *
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (Math.abs(q - 1) < EPS) {
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
        int distanceX = grassX - right;
        int distanceY = grassY - up;

        if (distanceX <= 0 || distanceY <= 0) {
            return 1;
        }

        int xPerDay = right - left;
        int yPerDay = up - down;

        if (xPerDay <= 0 && yPerDay <= 0) {
            return Integer.MAX_VALUE;
        }

        int xDays = (int) Math.ceil((double) distanceX / xPerDay) + 1;
        int yDays = (int) Math.ceil((double) distanceY / yPerDay) + 1;

        if (xDays > 0 && yDays > 0) {
            return Math.min(xDays, yDays);
        }

        return Math.max(xDays, yDays);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int i = 1;
        int number = n;

        while (i < order) {
            number /= 16;
            i++;
        }
        number %= 16;

        return (char) (number < 10 ? '0' + number : 'A' + number - 10);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        char min = 'F';
        int index = 0;
        int i = 0;
        long number = a;

        while (number > 0) {
            if ((int) (number % 16) < min) {
                min = (char) (number % 16);
                index = i;
            }

            if (min == 0) {
                break;
            }
            number /= 16;
            i++;
        }
        return (byte) (index + 1);
    }
}
