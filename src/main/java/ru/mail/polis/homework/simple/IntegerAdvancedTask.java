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
        long sum = 0;

        for (int i = 0; i < n; i++) {
            sum += a * Math.pow(q, i);
        }
        return sum;
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
        if (up >= grassY || right >= grassX)
            return 1;
        double dx = right - left;
        double dy = up - down;
        if (dx <= 0 && dy <= 0) {
            return Integer.MAX_VALUE;
        }
        int daysForRightBorder = Integer.MAX_VALUE;
        int daysForTopBorder = Integer.MAX_VALUE;
        if (dy > 0) {
            daysForTopBorder = (int) Math.ceil((grassY - up) / dy + 1);
        }
        if (dx > 0) {
            daysForRightBorder = (int) Math.ceil((grassX - right) / dx + 1);
        }
        return Math.min(daysForRightBorder, daysForTopBorder);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int decimal = n;

        for (int i = 1; i < order; i++) {
            decimal /= 16;
        }
        int digit = decimal % 16;
        switch (digit) {
            case 10:
                return 'A';
            case 11:
                return 'B';
            case 12:
                return 'C';
            case 13:
                return 'D';
            case 14:
                return 'E';
            case 15:
                return 'F';
            default:
                return (char) (digit + '0');
        }
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte index = 0;
        byte minDigit = 17;
        long number = a;

        byte i = 1;
        while (number != 0) {
            byte currentDigit = (byte) (number % 16);
            if (currentDigit < minDigit) {
                minDigit = currentDigit;
                index = i;
            }
            ++i;
            number /= 16;
        }
        return index;
    }

}
