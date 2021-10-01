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
        double epsilon = 0.000001d;
        if (Math.abs(1 - q) < epsilon) {
            return a * n;
        }
        return (long) (a * (1 - Math.pow(q, n)) / (1 - q));
    }

    /**
     * Гусеница ползает по столку квадратами по часовой стрелке. За день она двигается следующим образом:
     * сначала наверх на up, потом направо на right. Ночью она двигается вниз на down и налево на left.
     * Сколько суток понадобится гусенице, чтобы доползти до правого верхнего поля с травой?
     * Если она этого никогда не сможет сделать, Верните число Integer.MAX_VALUE;
     * Считаем, что на каждой клетке с координатами >= grassX или >= grassY находится трава
     * Пример: (10, 3, 5, 5, 11, 20) -> 2
     */
    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
        if (up >= grassY || right >= grassX) {
            return 1;
        }

        int changeX = right - left;
        int changeY = up - down;
        int remainingX = grassX - right;
        int remainingY = grassY - up;
        int daysX = changeX > 0 ? (int) Math.ceil((double) remainingX / changeX + 1) : Integer.MAX_VALUE;
        int daysY = changeY > 0 ? (int) Math.ceil((double) remainingY / changeY + 1) : Integer.MAX_VALUE;
        return Integer.min(daysX, daysY);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int nCopy = n;
        for (int i = 0; i < order - 1; i++) {
            nCopy /= 16;
        }
        int number = nCopy % 16;
        if (number <= 9) {
            return (char) (number + '0');
        }
        return (char) ('A' - 10 + number);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long aCopy = a;
        byte minIndex = 0;
        int min = 16;
        byte i = 1;
        while (aCopy > 0) {
            if (aCopy % 16 < min) {
                min = (int) aCopy % 16;
                minIndex = i;
            }
            i++;
            aCopy /= 16;
        }

        return minIndex;
    }

}
