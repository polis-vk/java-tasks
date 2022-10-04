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
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    // Вычисляется сумма геометрической прогрессии по формуле при q != 1, иначе - сумма одинаковых чисел
    public static long progression(int a, double q, int n) {
        return (long) (Double.compare(q, 1) != 0 ? a * (Math.pow(q, n) - 1) / (q - 1) : a * n);
    }

    /**
     * Гусеница ползает по столу квадратами по часовой стрелке. За день она двигается следующим образом:
     * сначала наверх на up, потом направо на right. Ночью она двигается вниз на down и налево на left.
     * Сколько суток понадобится гусенице, чтобы доползти до поля с травой?
     * Считаем, что на каждой клетке с координатами >= grassX или >= grassY находится трава
     * Если она этого никогда не сможет сделать, Верните число Integer.MAX_VALUE;
     * Пример: (10, 3, 5, 5, 20, 11) -> 2
     */
    /*
     * Сначала вычисляется расстояние до поля с травой без одного up или right, так как после
     * последнего шага алгоритом завершится. Если гусеница не может доползти до поля за 1 день,
     * то для ее перемещения вычисляется условный шаг, с которым она приближается к полю за сутки
     * по каждому напрявлению. Если шаг положительный - то гусеница доползет до границы поля за
     * количество шагов, равное оставшемуся растоянию (расстояние до границы - up или right),
     * поделенному на длину шага с округлением вверх + 1 шаг. В итоге выбирается наименьшее
     * число дней, за которое гусеница доползет до одной из границ поля.
     */
    public static int snake(int up, int right, int down, int left, int grassX, int grassY) {
        int previousY = Math.max(0, grassY - up);
        int previousX = Math.max(0, grassX - right);
        if (previousY == 0 || previousX == 0) {
            return 1;
        }

        int stepUp = up - down;
        int stepRight = right - left;
        int daysY = stepUp > 0 ? (int) Math.ceil((double) previousY / stepUp) + 1 : Integer.MAX_VALUE;
        int daysX = stepRight > 0 ? (int) Math.ceil((double) previousX / stepRight) + 1 : Integer.MAX_VALUE;
        return Math.min(daysY, daysX);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    /*
     * Число переводится в 16-ый формат до нужного разряда, после чего цифра переводися в char
     * как номер символа в ASCII таблице.
     */
    public static char kDecimal(int n, int order) {
        int quotient = n;
        byte radix = 16;
        for (int i = 0; i < order - 1; i++) {
            quotient /= radix;
        }
        quotient %= radix;
        return (char) (quotient < 10 ? quotient + '0' : quotient - 10 + 'A');
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long quotient = a;
        byte radix = 16;
        byte minimum = 16;
        byte minimumIndex = 0;
        byte i = 1;
        byte hexDigit;
        while (quotient != 0) {
            hexDigit = (byte) (quotient % radix);
            if (hexDigit < minimum) {
                minimum = hexDigit;
                minimumIndex = i;
            }
            quotient /= radix;
            i++;
        }
        return minimumIndex;
    }
}
