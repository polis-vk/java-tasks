package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */


public class IntegerAdvancedTask {

    private static final double EPS = 1e-10; // для сравнение double'в
    private static final byte ASCII_VALUE_START_TO_LETTERS = 55; // для перевода в букву в 16 системе
    private static final byte ASCII_VALUE_START_TO_DIGIT = 48;  // для перевода в цифру в 16 системе
    private static final byte RADIX = 16;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        return (Math.abs(q - 1) <= EPS) ? a * n : (long) (a * (Math.pow(q, n) - 1) / (q - 1)); // формула геометрической прогрессии
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
        if (up <= down && right <= left) {
            return Integer.MAX_VALUE;
        }

        int stepX = 0;
        int stepY = 0;
        boolean flagDayOrNight = true;
        int answerCntDays = 1;

        while (stepX < grassX && stepY < grassY) {
            if (flagDayOrNight) {
                stepY += up;
                stepX += right;
                flagDayOrNight = false;
            } else {
                stepY -= down;
                stepX -= left;
                answerCntDays++;
                flagDayOrNight = true;
            }
        }

        return answerCntDays;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */

    public static char kDecimal(int n, int order) {
        int translatedDigitValue = (int) (n / Math.pow(RADIX, order - 1)); // перевод до нужного разряда
        int answerDigit = translatedDigitValue % RADIX;

        if (translatedDigitValue % RADIX >= 10) {
            answerDigit += ASCII_VALUE_START_TO_LETTERS; // для перевода в буквенное значение
        } else {
            answerDigit += ASCII_VALUE_START_TO_DIGIT; // для перевода в цифру
        }

        return (char) answerDigit;
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long translatedDigitValue = a;

        byte positionMinimalDigit = 0;
        byte iterator = 1;
        long minimalDigit = RADIX;

        while (translatedDigitValue > 0) {
            byte lastDigit = (byte) (translatedDigitValue % RADIX);
            if (lastDigit < minimalDigit) {
                minimalDigit = lastDigit;
                positionMinimalDigit = iterator;
            }
            translatedDigitValue /= RADIX;
            iterator++;
        }

        return positionMinimalDigit;
    }
}
