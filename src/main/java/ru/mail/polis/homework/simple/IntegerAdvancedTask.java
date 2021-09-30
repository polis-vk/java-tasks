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
        long res = 0;
        for (int i = 0; i < n; i++) {
            res += a * Math.pow(q, i);
        }
        return res;
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
        if (up >= grassY || right >= grassX) {
            return 1;
        } else if (up <= down && right <= left) {
            return Integer.MAX_VALUE;
        }

        int x = 0;
        int y = 0;
        int daysCount = 0;

        while (x < grassX && y < grassY) {
            y += up;
            x += right;
            daysCount++;

            if (x >= grassX || y >= grassY) {
                break;
            }

            y -= down;
            x -= left;
        }
        return daysCount;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int tmpN = n;
        for (int i = 0; i < order - 1; i++) {
            tmpN /= 16;
        }
        return Character.toUpperCase(Character.forDigit(tmpN % 16, 16));
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long tmpA = a;
        byte indexOfMin = 1;
        byte i = 1;
        long tmpMin = tmpA % 16;
        while (tmpA > 0 && tmpMin != 0) {
            if (tmpA % 16 < tmpMin) {
                tmpMin = tmpA % 16;
                indexOfMin = i;
            }
            i++;
            tmpA /= 16;
        }
        return indexOfMin;
    }
}
