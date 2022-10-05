package ru.mail.polis.homework.simple;


import java.util.ArrayList;

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
        return (long)(a * (( Math.pow(q, n) - 1) / ( q - 1 )));
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
        int x = 0, y = 0;
        int day = 0;
        while(true) {
            if(y + up >= grassY || x + right >= grassX) {
                return ++day;
            }
            if(up < down && right < left) {
                return Integer.MAX_VALUE;
            }
            x += (right - left);
            y += (up - down);
            day++;
        }

    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        ArrayList<Integer> digits16 = new ArrayList<>();

        while(n / 16 != 0 && n > 15) {
            digits16.add(n % 16);
            n /= 16;

        }
        switch (digits16.get(order - 1)) {
            case 10: return 'A';
            case 11: return 'B';
            case 12: return 'C';
            case 13: return 'D';
            case 14: return 'E';
            case 15: return 'F';
            default: return (char)(digits16.get(order - 1).intValue());
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

        ArrayList<Long> digits16 = new ArrayList<>();
        byte min = Byte.MAX_VALUE;
        byte iMin = -1;
        byte i = 0;
        while(a / 16 != 0 && a > 15) {
            i++;
            if(a % 16 < min) {
                min = (byte)(a % 16);
                iMin = i;
            }
            a /= 16;
        }
        return iMin;
    }

}
