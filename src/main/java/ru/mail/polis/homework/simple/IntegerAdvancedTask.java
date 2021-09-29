package ru.mail.polis.homework.simple;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

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
        if (q == 0.0 || n == 1) {
            return a;
        } else if (q == 1.0) {
            return (long) a * n;
        } else {
            return (long) ((a * (1 - Math.pow(q, n))) / (1 - q));
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
        if (up - down <= 0 && up < grassY && right - left <= 0 && right < grassX) {
            return Integer.MAX_VALUE;
        } else {
            int days = 0;
            int snakeX = 0;
            int snakeY = 0;
            while (snakeX < grassX && snakeY < grassY) {
                days++;
                snakeY = snakeY + up;
                if (snakeY >= grassY) {
                    break;
                }
                snakeX = snakeX + right;
                if (snakeX >= grassX) {
                    break;
                }
                snakeY = snakeY - down;
                if (snakeY >= grassY) {
                    break;
                }
                snakeX = snakeX - left;
                if (snakeX >= grassX) {
                    break;
                }
            }
            return days;
        }
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        ArrayList<Character> digits = new ArrayList<Character>();
        //я так поняла, что нельзя пользоваться стрингами в плане метода toHexString()
        String dig = "0123456789ABCDEF";
        while (n > 0) {
            digits.add(dig.charAt(n % 16));
            n = n / 16;
        }
        return digits.get(order - 1);
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        ArrayList<Character> digits = new ArrayList<Character>();
        String dig = "0123456789ABCDEF";
        char min = Character.MAX_VALUE;
        while (a > 0) {
            digits.add(dig.charAt(Math.floorMod(a, 16)));
            a = a / 16;
        }
        int number = 0;
        Collections.reverse(digits);
        for (int i = digits.size() - 1; i >= 0; i--) {
            if (digits.get(i) < min) {
                min = digits.get(i);
                number = digits.size() - i;
            }
        }
        return (byte) number;
    }

}
