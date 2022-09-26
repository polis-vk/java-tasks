package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final double EPS = 1e-10;
    private static final int HEX = 16;

    // Function-helper's
    private static char getDigitInHex(int n) {
        if (n >= 10) {
            return Character.toUpperCase(Character.forDigit(n, HEX));
        }
        return (char) ('0' + n);
    }

    private static boolean isOnTheGrass(int x, int y, int grassX, int grassY) {
        return x >= grassX || y >= grassY;
    }

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        double result = 0;
        double currentMultiplier = 1;
        for (int i = 0; i < n; i++) {
            result += a * currentMultiplier;
            currentMultiplier *= q;
        }
        return (long) result;
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
        // Сразу делаем проверку краевых вариантов

        // Проверяем, находится ли в данный момент гусеница в траве
        if (isOnTheGrass(0, 0, grassX, grassY)) {
            return 0;
        }

        // Проверяем, может ли гусеница достичь травы в первые полдня
        if (isOnTheGrass(right, up, grassX, grassY)) {
            return 1;
        }

        // Изменение позиции гусеницы по вертикали и горизонтали за сутки
        int deltaY = up - down;
        int deltaX = right - left;

        // Количество дней, необходимое, чтобы добраться до верхней и правой границ
        int yDays;
        int xDays;

        // Проверяем движется ли гусеница вверх или вправо в сторону травы
        // Если движется, то рассчитываем количество дней, необходимое для того, чтобы сделать последний шаг.
        // Сразу же идёт проверка на то, может ли добраться до травы гусеница вообще
        if (deltaY > 0) {
            yDays = (int) Math.ceil((float) (grassY - up) / deltaY) + 1;
        } else {
            yDays = Integer.MAX_VALUE;
        }

        if (deltaX > 0) {
            xDays = (int) Math.ceil((float) (grassX - right) / deltaX) + 1;
        } else {
            xDays = Integer.MAX_VALUE;
        }

        return Math.min(xDays, yDays);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */

    public static char kDecimal(int n, int order) {
        int number = n;
        byte currentOrder = 1;
        while (currentOrder < order) {
            number /= HEX;
            currentOrder++;
        }
        return getDigitInHex((byte) (number % HEX));
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long number = a;

        byte currentDigit = (byte) (number % HEX);
        byte minDigit = currentDigit;

        byte minDigitOrder = 1;
        byte currentOrder = 1;

        while (number > 0) {
            if (currentDigit < minDigit) {
                minDigit = currentDigit;
                minDigitOrder = currentOrder;
            }
            number /= HEX;
            currentDigit = (byte) (number % HEX);
            currentOrder++;
        }

        return minDigitOrder;
    }

}
