package ru.mail.polis.homework.simple;


import java.util.Locale;

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
     *
     * Пример: (1, 2, 3) -> 7
     */
    public static long progression(int a, double q, int n) {
        double sum = a;
        double temp = a;
        for (int i = 1; i <= n - 1; i++) {
            temp *= q;
            sum += temp;
        }
        return (long) sum;
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
        int dx = right - left;
        int dy = up - down;
        int days = 1;
        int lastStepX = grassX - right;
        int lastStepY = grassY - up;
        if (lastStepX <= 0 || lastStepY <= 0) {
            return days;
        }
        if ((dx <= 0) && (dy <= 0)) {
            return Integer.MAX_VALUE;
        }

        double speedX = (double) (lastStepX) / dx;
        double speedY = (double) (lastStepY) / dy;
        if ((int)speedX != speedX) {
            speedX++;
        }
        if ((int)speedY != speedY) {
            speedY++;
        }
        if (speedX > 0 && speedY > 0) {
            days += Math.min(speedX, speedY);
        } else if (speedX > 0) {
            days += speedX;
        } else if (speedY > 0) {
            days += speedY;
        } else {
            return Integer.MAX_VALUE;
        }

        /*while (!(posX >= grassX && posY >= grassY)) {
            posY += up;
            posX += right;
            if (posX >= grassX && posY >= grassY) {
                return days;
            }
            posY -= down;
            posX -= left;
            days++;
        }*/


        return days;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int i = 1;
        while (i < order) {
            n /= 16;
            i++;
        }
        int temp = n % 16;
        if (temp < 10) {
            temp += 48;
        } else {
            temp += 55;
        }
        return (char) temp;
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        char min = 'F';
        int pos = 0;
        int i = 0;
        while(a > 0) {
            if ((char) (a % 16) < min) {
                min = (char) (a % 16);
                pos = i;
            }
            a /= 16;
            i++;
        }
        return (byte) (pos + 1);
    }
}

