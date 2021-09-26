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
        long term = a;
        for (int i = 1; i < n; i++) {
            term *= q;
            a += term;
        }
        return a;
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
        int dx = right - left;
        int dy = up - down;
        if ((dx <= 0) && (dy <= 0) && (right < grassX) && (up < grassY)) {
            return Integer.MAX_VALUE;
        }
        if ((right >= grassX) || (up >= grassY)) {
            return 1;
        }
        // Если мы достигнем какой-либо из этих точек после окончания дня
        // то следующим же шагом змейка доберется до травы.
        int needReachX = grassX - right;
        int needReachY = grassY - up;
        int resultForX = ((needReachX % dx == 0) ? (needReachX / dx) : (needReachX / dx + 1)) + 1;
        int resultForY = ((needReachY % dy == 0) ? (needReachY / dy) : (needReachY / dy + 1)) + 1;
        if (dx > 0 && dy > 0) {
            return Math.min(resultForX, resultForY);
        } else if (dx > 0) {
            return resultForX;
        }
        return resultForY;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        for (int i = 0; i < order - 1; i++) {
            n /= 16;
        }
        int digit = n % 16;
        switch (digit) {
            case 15:
                return 'F';
            case 14:
                return 'E';
            case 13:
                return 'D';
            case 12:
                return 'C';
            case 11:
                return 'B';
            case 10:
                return 'A';
            default:
                return (char) (digit + '0');
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
        byte count = 1;
        byte minNumberIndex = -1;
        byte minNumber = 16;
        byte candidate;
        while (a != 0) {
            candidate = (byte) (a % 16);
            if (candidate < minNumber) {
                minNumber = candidate;
                minNumberIndex = count;
            }
            a /= 16;
            count++;
        }
        return minNumberIndex;
    }

}
