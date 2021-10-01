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
        return (q == 1) ? (long) a * n : (long) (a * (1 - Math.pow(q, n)) / (1 - q));
    }

    /**
     * Гусеница ползает по столку квадратами по часовой стрелке. За день она двигается следующим образом:
     * сначала наверх на up, потом направо на right. Ночью она двигается вниз на down и налево на left.
     * Сколько суток понадобится гусенице, чтобы доползти до правого верхнего поля с травой?
     * Если она этого никогда не сможет сделать, Верните число Integer.MAX_VALUE;
     * Считаем, что на каждой клетке с координатами >= grassX или >= grassY находится трава
     * Пример: (10, 3, 5, 5, 20, 11) -> 2
     */
    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
        int x = 0;
        int y = 0;
        int i = 0;
        int xPrev = 0;
        int yPrev = 0;
        while (x < grassX && y < grassY) {
            i++;
            x += right;
            y += up;
            if (x >= grassX || y >= grassY) {
                return i;
            }
            x -= left;
            y -= down;
            if (i == 1 && x <= xPrev && y <= yPrev) {
                return Integer.MAX_VALUE;
            }
            xPrev = x;
            yPrev = y;
        }
        return i;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int current = n;
        int i = 1;
        int integerPart = 1;
        byte residue;
        while (integerPart > 0) {
            integerPart = current >> 4;
            residue = (byte) (current - (integerPart << 4));
            if (i == order) {
                switch (residue) {
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
                        return (char) (residue + '0');
                }
            }
            current = integerPart;
            i++;
        }
        return '0';
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long currentDecimal = a;
        long integerPart = 1;
        byte minNumber = 16;
        byte residue;
        byte minIndex = 1;
        byte i = 1;
        while (integerPart > 0) {
            integerPart = currentDecimal >> 4;
            residue = (byte) (currentDecimal - integerPart * 16);
            if (residue < minNumber) {
                minNumber = residue;
                minIndex = i;
            }
            currentDecimal = integerPart;
            i++;
        }
        return minIndex;
    }
}
