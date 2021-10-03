package ru.mail.polis.homework.simple;


import java.util.InputMismatchException;

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
        long answer = 0;
        double prevQ = 1;
        for (int i = 0; i < n; i++) {
            answer += a * prevQ;
            prevQ *= q;
        }
        return answer;
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
        if (up >= grassY || right >= grassX) {
            return 1;
        }
        int ansY = Integer.MAX_VALUE;
        int ansX = Integer.MAX_VALUE;
        if (up > down) {
            ansY = (int) Math.ceil((grassY - up) / ((up - down) * 1.0)) + 1;
        }
        if (right > left) {
            ansX = (int) Math.ceil((grassX - right) / ((right - left) * 1.0)) + 1;
        }
        return Math.min(ansY, ansX);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int inputNum = n;
        int index = order;
        char[] symbols = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        for (int i = 0; i < index - 1; i++) {
            inputNum /= 16;
        }
        return symbols[inputNum % 16];
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long inputNumber = a;
        byte minNum = 16;
        byte numOfMin = 0;
        if (inputNumber < 0) {
            throw new InputMismatchException("Input numbers must be above zero");
        }
        for (int i = 1; inputNumber > 0; i++) {
            if (inputNumber % 16 < minNum) {
                if (inputNumber % 16 == 0) {
                    return (byte) i;
                }
                minNum = (byte) (inputNumber % 16);
                numOfMin = (byte) i;
            }
            inputNumber /= 16;
        }
        return numOfMin;
    }
}
