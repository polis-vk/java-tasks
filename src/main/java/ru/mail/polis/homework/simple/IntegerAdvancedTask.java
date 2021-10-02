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
        int member = a;
        long sum = 0;

        for (int i = 0; i < n; i++) {
            sum += member;
            member *= q;
        }

        return sum;
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
        if (up >= grassY || right >= grassX)
            return 1;
        int xSpeed = right - left;
        int ySpeed = up - down;
        if (xSpeed <= 0 && ySpeed <= 0)
            return Integer.MAX_VALUE;
        return (int) Math.max(Math.ceil(((double) grassX - right) / xSpeed), Math.ceil(((double) grassY - up) / ySpeed)) + 1;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int result = (int) ((n % (long) Math.pow(16, order)) / (long) Math.pow(16, order - 1));
        if (result < 10)
            return (char) (result + '0');
        return (char) (result + 'A' - 10);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte minDigit = (byte) (a % 16);
        byte minDigitPosition = 1;
        byte currentPosition = 2;

        a /= 16;
        while (a != 0) {
            if (minDigit > a % 16) {
                minDigit = (byte) (a % 16);
                minDigitPosition = currentPosition;
            }
            currentPosition++;
            a /= 16;
        }
        return minDigitPosition;
    }

}
