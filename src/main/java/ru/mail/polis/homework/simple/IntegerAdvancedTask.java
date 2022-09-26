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
        long result = a;
        for (int i = 1; i < n; i++) {
            result += a * Math.pow(q, i);
        }
        return result;
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
        if ((up >= grassY && grassY > 0) || (right >= grassX && grassX > 0)
                || (-down <= grassY && grassY < 0) || (-left <= grassX && grassX < 0)) {
            return 1;
        }
        int dailyX = right - left;
        int dailyY = up - down;
        int daysX = 0;
        int daysY = 0;
        if (dailyX > 0 && grassX > 0) {
            daysX = (grassX - right - 1) / dailyX + 2;
        }
        if (dailyX < 0 && grassX < 0) {
            daysX = grassX / dailyX;
        }
        if (dailyY > 0 && grassY > 0) {
            daysY = (grassY - up - 1) / dailyY + 2;
        }
        if (dailyY < 0 && grassY < 0) {
            daysY = grassY / dailyY;
        }
        if (daysX == 0 && daysY == 0) {
            return Integer.MAX_VALUE;
        } else if (daysX == 0 || (daysY != 0 && daysY <= daysX)) {
            return daysY;
        } else {
            return daysX;
        }
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        byte result = (byte) (n / (int) Math.pow(16, order - 1) % 16);
        if (result < 10) {
            return (char) (result + 48); // 0..9
        } else {
            return (char) (result + 55); // A..F
        }
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte minOrder = 1;
        byte minDigit = 16;
        long num = a;
        for (byte i = 1; num > 0; i++) {
            byte currentDigit = (byte) (num % 16);
            if (currentDigit < minDigit) {
                minDigit = currentDigit;
                minOrder = i;
            }
            num /= 16;
        }
        return minOrder;
    }

}
