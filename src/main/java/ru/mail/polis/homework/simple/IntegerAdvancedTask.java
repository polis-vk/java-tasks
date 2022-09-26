package ru.mail.polis.homework.simple;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final double EPS = 1e-10;
    private static final char[] HEX_VALUES = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G'};

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        return (long) (Math.abs(q - 1) > EPS ? (a * (Math.pow(q, n) - 1) / (q - 1)) : a * n);
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
        int onGrassX = grassX - right;
        int onGrassY = grassY - up;
        if (onGrassX <= 0 || onGrassY <= 0) {
            return 1;
        }

        int dX = right - left;
        int dY = up - down;
        if (dX <= 0 && dY <= 0) {
            return Integer.MAX_VALUE;
        }

        double dayXToGrass = Math.ceil(onGrassX * 1. / dX) + 1;
        double dayYToGrass = Math.ceil(onGrassY * 1. / dY) + 1;
        return (int) (dayXToGrass < 0 && dayYToGrass < 0
                ? Math.min(dayXToGrass, dayYToGrass) : Math.max(dayXToGrass, dayYToGrass));
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int num = n;

        for (int i = 1; num > 16 && i < order; i++) {
            num /= 16;
        }

        return HEX_VALUES[(num % 16)];
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long num = a;
        byte minValue = Byte.MAX_VALUE;
        byte counter = 1;
        byte tempCounter = counter;

        while (num > 16) {
            if ((num % 16) < minValue) {
                minValue = (byte) (num % 16);
                tempCounter = counter;
            }
            num /= 16;
            counter++;
        }

        return num < minValue ? counter : tempCounter;
    }

}
