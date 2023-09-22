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
        if(q == 1.0) return (long)(a*n);
        return (long)(a*(Math.pow(q, n)-1))/(long)(q-1);
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
        int currX = 0, currY = 0, xday = 0, yday = 0;
        //Y:
        if(up >= grassY) {yday = 1;}
        else
        {
            if(up-down <= 0) {yday = Integer.MAX_VALUE;}
            else
            {
                yday = 1;currY = up;
                while(currY < grassY)
                {
                    ++yday;
                    currY += (up-down);
                }
            }
        }

        if(right >= grassX) {xday = 1;}
        else
        {
            if(right-left <= 0) {xday = Integer.MAX_VALUE;}
            else
            {
                xday = 1;currX = right;
                while(currX < grassX)
                {
                    ++xday;
                    currX += (right-left);
                }
            }
        }
        return Math.min(xday, yday);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int curr = 0; char ans;
        while(true)
        {
            ++curr;
            int c_ = n % 16;
            char c = Integer.toHexString(c_).toUpperCase().charAt(0);
            n /= 16;
            if(curr == order) {ans = c; break;}
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
    public static long minNumber(long n) {
        long pos = 0, curr = 0; long ans = Integer.MAX_VALUE;
        if(n == Integer.MAX_VALUE) return 16;
        while(n > 0)
        {
            ++curr;
            long c = n % 16;
            if(c < ans) {
                pos = curr;
                ans  = c;
            }
            n /= 16;
        }
        return pos;
    }

}
