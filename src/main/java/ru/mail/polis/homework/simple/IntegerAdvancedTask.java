package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    final static double EPS = 1e-15;
    
    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (Math.abs(q - 1.0) < EPS) {
            return (long) a * n;
        }
        return (long) (a * (1 - Math.pow(q, n)) / (1 - q));
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
        if (up >= grassY || right >= grassX) {
            return 1;
        }
        if (down >= up && left >= right) {
            return Integer.MAX_VALUE;
        }
        int step1 = up - down;
        int step2 = right - left;
        int heightBeforeLastClimb = grassY - up;
        int widthBeforeLastClimb = grassX - right;
        int daysUp = heightBeforeLastClimb % step1 == 0 ? heightBeforeLastClimb / step1 + 1 : heightBeforeLastClimb / step1 + 2;
        int daysRight = widthBeforeLastClimb % step2 == 0 ? widthBeforeLastClimb / step2 + 1 : widthBeforeLastClimb / step2 + 2;
        if (daysUp < 0) {
            return daysRight;
        }
        if (daysRight < 0) {
            return daysUp;
        }
        return Integer.min(daysUp, daysRight);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int currentDiv = n;
        for (int i = 0; i < order - 1; i++) {
            currentDiv /= 16;
        }
        char orderNumber = Character.forDigit(currentDiv % 16, 16);
        return orderNumber < 10 ? orderNumber : Character.toUpperCase(orderNumber);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte minDigit = Byte.MAX_VALUE;
        byte minDigitIndex = 1;
        byte currentDigit = (byte) (a % 16);
        long currentDiv = a / 16;
        for (byte i = 1; currentDiv != 0 && minDigit != 0; i++) {
            if (currentDigit < minDigit) {
                minDigit = currentDigit;
                minDigitIndex = i;
            }
            currentDigit = (byte) (currentDiv % 16);
            currentDiv /= 16;
        }
        return minDigitIndex;
    }

}
