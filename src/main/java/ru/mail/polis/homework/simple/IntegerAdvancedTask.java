package ru.mail.polis.homework.simple;


/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final double ERROR = 0.000001;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        if (Math.abs(q - 0) < ERROR) {
            return a;
        } else if (Math.abs(q - 1) < ERROR) {
            return (long) a * n;
        }
        return (long) (a * (Math.pow(q, n) - 1) / (q - 1));
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
        if ((up > grassX) || (right > grassY)) {
            return 1;
        } else if ((grassX <= 0) || (grassY <= 0)) {
            return 0;
        }

        int dayX = right - left;
        int dayY = up - down;
        if ((dayX <= 0) && (dayY <= 0)) {
            return Integer.MAX_VALUE;
        } else if (dayX <= 0) {
            return (int) (Math.ceil((float) (grassY - up) / dayY) + 1);
        } else if (dayY <= 0) {
            return (int) (Math.ceil((float) (grassX - right) / dayX) + 1);
        }
        return (int) Math.min(Math.ceil((float) (grassY - up) / dayY) + 1, Math.ceil((float) (grassX - right) / dayX) + 1);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        return Character.toUpperCase(Character.forDigit((int) (n / Math.pow(16, order - 1) % 16), 16));
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte min = 16;
        byte minIndex = 1;
        byte i = 1;
        long number = a;
        while (number > 0) {
            if (number % 16 < min) {
                min = (byte) (number % 16);
                minIndex = i;
            }
            if (min == 0) {
                break;
            }
            number /= 16;
            i++;
        }
        return minIndex;
    }

}
