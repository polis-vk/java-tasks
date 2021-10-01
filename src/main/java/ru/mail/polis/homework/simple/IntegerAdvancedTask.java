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
        if (Double.compare(q, 1.0) == 0) {
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
        int currentX = 0;
        int currentY = 0;
        if (currentX >= grassX || currentY >= grassY) {
            return 0;
        }
        int daysCounter = 1;
        int lastX = 0;
        int lastY = 0;
        int lastDeltaX = grassX;
        int lastDeltaY = grassY;
        while (currentX < grassX && currentY < grassY) {
            currentX += right;
            currentY += up;
            if (currentX >= grassX || currentY >= grassY) {
                return daysCounter;
            }
            currentX -= left;
            currentY -= down;
            int currentDeltaX = lastX + currentX;
            int currentDeltaY = lastY + currentY;
            if (currentDeltaX >= lastDeltaX && currentDeltaY >= lastDeltaY) {
                return Integer.MAX_VALUE;
            }
            lastX = currentX;
            lastY = currentY;
            lastDeltaX = currentDeltaX;
            lastDeltaY = currentDeltaY;
            daysCounter++;
        }
        return daysCounter;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int dividend = n;
        int newDividend;
        int result = 0;
        int indexOfCurrentDischarge = 0;
        while (indexOfCurrentDischarge < order) {
            newDividend = dividend / 16;
            result = dividend - newDividend * 16;
            dividend = newDividend;
            indexOfCurrentDischarge++;
        }
        if (result > 10) {
            return (char) (result + 55);
        }
        return (char) (result + 48);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long divided = a;
        long newDivided;
        byte min = 17;
        byte minimalIndex = -1;
        byte currentDischarge;
        byte indexOfCurrentDischarge = 1;
        while (divided > 15) {
            newDivided = divided / 16;
            currentDischarge = (byte) (divided - newDivided * 16);
            if (currentDischarge < min) {
                min = currentDischarge;
                minimalIndex = indexOfCurrentDischarge;
                if (min == 0) {
                    return minimalIndex;
                }
            }
            indexOfCurrentDischarge++;
            divided = newDivided;
        }
        if (divided < min) {
            return indexOfCurrentDischarge;
        }
        return minimalIndex;
    }

}
