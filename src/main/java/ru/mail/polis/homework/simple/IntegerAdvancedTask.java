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
        long answer = a;
        for (int degree = 1; degree < n; degree++) {
            answer += a * Math.pow(q, degree);
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

        int speedX = right - left;
        int speedY = up - down;

        int dayX = Integer.MAX_VALUE;
        int dayY = Integer.MAX_VALUE;

        //(grassX - right)... + 1 because of case, when snake come to grass in the middle of day
        if (speedX > 0) {
            dayX = (int) Math.ceil((double) (grassX - right) / speedX) + 1;
        }
        if (speedY > 0) {
            dayY = (int) Math.ceil((double) (grassY - up) / speedY) + 1;
        }

        return Math.min(dayX, dayY);
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */

    public static int computeNumInOrder(long n, int order) {
        long numToOrder = n % (long) Math.pow(16, order);

        int answer10 = (int) (numToOrder / (long) Math.pow(16, order - 1));
        return answer10;
    }

    public static char kDecimal(int n, int order) {
        int answer10 = (int) computeNumInOrder(n, order);

        //Converting to char
        if (answer10 <= 9)
            return (char) (answer10 + 48);
        return (char) (answer10 + 55);

    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte getMaxOrder(long a) {
        byte maxOrder = 1;
        while (a % (long) Math.pow(16, maxOrder) != a && (long) Math.pow(16, maxOrder) < Long.MAX_VALUE) {
            maxOrder++;
        }
        return maxOrder;
    }

    public static byte minNumber(long a) {
        byte order = 1;
        int min = computeNumInOrder(a, order);
        byte minInd = order;
        order++;

        byte maxOrder = getMaxOrder(a);
        while (order <= maxOrder) {
            int numInOrder = computeNumInOrder(a, order);
            if (numInOrder < min) {
                min = numInOrder;
                minInd = order;
            }
            order++;
        }
        return minInd;
    }

}
