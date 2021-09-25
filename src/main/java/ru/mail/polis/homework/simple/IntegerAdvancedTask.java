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
        if (q == 1.0) {
            return (long) a * n;
        } else {
            return (long) (a * (1 - Math.pow(q, n)) / (1 - q));
        }
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
        int upMove = up - down; // смещение вверх
        int rightMove = right - left; // смещение вправо
        if (up >= grassY || right >= grassX) {
            return 1;
        }
        if (upMove <= 0 && rightMove <= 0) {
            return Integer.MAX_VALUE;
        }
        int upMoveCnt = (int) Math.ceil((double) (grassY - up) / upMove);
        int rightMoveCnt = (int) Math.ceil((double) (grassX - right) / rightMove);
        if (upMoveCnt > 0 && rightMoveCnt > 0) {
            return Math.min(upMoveCnt, rightMoveCnt) + 1;
        } else {
            return Math.max(upMoveCnt, rightMoveCnt) + 1;
        }
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        StringBuilder hexNum = new StringBuilder();
        for (int i = 0; i < order; i++) {
            if (n % 16 > 9) {
                hexNum.append((char) (n % 16 + 55)); // Код символа 'A' - 65.
            } else {
                hexNum.append(n % 16);
            }
            n /= 16;
        }
        return hexNum.charAt(order - 1);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte minNum = 15;
        byte minPos = 1;
        byte iterator = 1;
        while (a != 0) {
            if (a % 16 < minNum) {
                minNum = (byte) (a % 16);
                minPos = iterator;
            }
            a /= 16;
            iterator++;
        }
        return minPos;
    }
}
