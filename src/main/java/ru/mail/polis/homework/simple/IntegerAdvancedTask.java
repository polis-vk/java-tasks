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
        return (long) (a * (1 - Math.pow(q, n)) / (1 - q)); //geometric progression sum formula
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
        int count = 1;
        int currentX = 0;
        int currentY = 0;
        if (currentX + up >= grassY || currentY + right >= grassX) return count;
        if (up - down <= 0 && right - left <= 0) return Integer.MAX_VALUE;
        while (true) {
            if (currentX + up >= grassY || currentY + right >= grassX) return count;
            else {
                currentX += (up - down);
                currentY += (right - left);
                count++;
            }
        }

    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        String hexValue = Integer.toHexString(n).toUpperCase();
        return hexValue.charAt(hexValue.length() - order);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        String hexValue = Long.toHexString(a).toUpperCase();
        byte min = Byte.MAX_VALUE;
        byte resIndex = 0;
        for (int i = hexValue.length() - 1; i >= 0; i--) {
            if (hexValue.charAt(i) < min) {
                resIndex = (byte) (hexValue.length() - i);
                min = (byte) hexValue.charAt(i);
            }
        }
        return resIndex;
    }

}
