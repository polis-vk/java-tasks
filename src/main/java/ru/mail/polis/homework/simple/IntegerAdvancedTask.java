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
        double delta = 1e-10;
        if (Math.abs(q - 1.0) < delta) {
            return (long) a * n;
        }
        return (long) (a * (1 - Math.pow(q, n)) / (1 - q));
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
        }
        int upMove = up - down; // смещение вверх
        int rightMove = right - left; // смещение вправо
        if (upMove <= 0 && rightMove <= 0) {
            return Integer.MAX_VALUE;
        }
        int upMoveCount = (int) Math.ceil((double) (grassY - up) / upMove);
        int rightMoveCount = (int) Math.ceil((double) (grassX - right) / rightMove);
        if (upMoveCount > 0 && rightMoveCount > 0) {
            return Math.min(upMoveCount, rightMoveCount) + 1;
        }
        return Math.max(upMoveCount, rightMoveCount) + 1;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int[] hexNums = new int[order];
        int buffer = n;
        for (int i = 0; i < order; i++) {
            if (buffer % 16 > 9) {
                hexNums[i] = buffer % 16 + 55; // Код символа 'A' - 65.
            } else {
                hexNums[i] = buffer % 16 + 48; // Код символа '0' - 48.
            }
            buffer /= 16;
        }
        return (char) hexNums[order - 1];
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        byte minNum = 15;
        byte minPos = 1;
        byte i = 1;
        long buffer = a;
        while (buffer != 0) {
            if (buffer % 16 < minNum) {
                minNum = (byte) (buffer % 16);
                minPos = i;
            }
            buffer /= 16;
            i++;
        }
        return minPos;
    }
}
