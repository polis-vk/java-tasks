package ru.mail.polis.homework.simple;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class IntegerAdvancedTask {

    private static final double EPS = 1e-10;
    private static final int numberSystem = 16;

    /**
     * Сумма первых n-членов геометрической прогрессии с первым элементом a и множителем r
     * a + aq + aq^2 + ... + aq^(n-1)
     * <p>
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        long progressionSum = 0;
        long multiplicationResult = 1;

        for (int power = 0; power < n; power++) {
            progressionSum += a * multiplicationResult;
            multiplicationResult *= q;
        }
        return progressionSum;
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
        int halfDay = 0;
        int x = 0;
        int y = 0;
        int dx;
        int dy;

        while (true) {
            dy = (halfDay % 2 == 0) ? up : -down;
            dx = (halfDay % 2 == 0) ? right : -left;
            halfDay++;
            x += dx;
            if (x >= grassX) {
                break;
            }
            y += dy;
            if (y >= grassY) {
                break;
            }
            if (halfDay == 2) {
                if (x <= 0 && y <= 0) return Integer.MAX_VALUE;
            }
        }
        return halfDay / 2 + 1;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */


    public static char kDecimal(int n, int order) {
        int digitOrder = (n / (int) Math.pow(numberSystem, order - 1)) % numberSystem;

        return (digitOrder > 9) ? (char) (digitOrder + 55) : (char) (digitOrder + 48);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        long number = a;
        long hexNumber = number % 16;
        long minHexNumber = hexNumber;
        byte minIndex = 1;
        byte counter = 1;

        while (number > 0) {

            if (minHexNumber > hexNumber) {
                minIndex = counter;
                minHexNumber = hexNumber;
            }

            number /= numberSystem;
            hexNumber = number % 16;
            counter++;
        }

        return minIndex;
    }

}
