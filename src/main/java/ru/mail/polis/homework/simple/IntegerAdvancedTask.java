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
    public static long progression(int a, double q, int n) {
        if (Math.abs(q - 1) < EPS) {
            return a * n;
        }
        return (long) ((a * (Math.pow(q, n) - 1)) / (q - 1));
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

        return Math.min(oneDimSnake(grassX, right, left), oneDimSnake(grassY, up, down));
    }

    private static int oneDimSnake(int height, int top, int bottom) {
        if (height <= top) {
            return 1;
        }
        if (top <= bottom) {
            return Integer.MAX_VALUE;
        }
        return (height - top - 1) / (top - bottom) + 2;
        // по идее, формула имеет вид (height - top) / (top - bottom) + 1,
        // но нам нужно округление вверх, поэтому так
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int inpNumber = n;
        for (int cnt = 0; cnt < order - 1; cnt++) {
            inpNumber /= 16;
        }
        char ans = (char) (inpNumber % 16);

        if (ans >= 10) {
            ans += 'A' - 10;
        } else {
            ans += '0';
        }

        return ans;
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long inpNumber = a;
        byte ans = -1;
        byte minNum = 16;
        byte cnt = 0;
        while (inpNumber != 0) {
            byte num = (byte) (inpNumber % 16);
            if (num < minNum) {
                minNum = num;
                ans = cnt;
            }
            inpNumber /= 16;
            cnt++;
        }
        return (byte) (ans + 1);
    }

}
