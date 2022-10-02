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
        long Sum = a;
        for (int i = 1; i < n; i++) {
            Sum += a * Math.pow(q, i);
        }
        return Sum;
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
        int x = 0;
        int y = 0;
        double startDistanceX = Math.abs(Math.pow(x - grassX, 2));
        double startDistanceY = Math.abs(Math.pow(y - grassY, 2));
        int days = 1;
        while (x < grassX && y < grassY) {

            x += right;
            y += up;
            if (x >= grassX || y >= grassY) {
                break;
            }
            x -= left;
            y -= down;
            double newDistanceX = Math.abs(Math.pow(x - grassX, 2));
            double newDistanceY = Math.abs(Math.pow(y - grassY, 2));
            if ((newDistanceX >= startDistanceX) && (newDistanceY >= startDistanceY)) {
                days = Integer.MAX_VALUE;
                break;
            }
            days++;
        }
        return days;
    }
    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int counterNumbers = 1;
        int buff = n;
        while (buff / 16 != 0) {
            buff = buff / 16;
            counterNumbers++;
        }
        int[] arrayOfNumbers = new int[counterNumbers];
        for (int i = 0; i < counterNumbers; i++) {
            int remainder = n % 16;
            n = n / 16;
            arrayOfNumbers[i] = remainder;
        }
        char result = ' ';
        int number = arrayOfNumbers[order - 1];
        switch (number) {
            case 0:
                result = '0';
                break;
            case 1:
                result = '1';
                break;
            case 2:
                result = '2';
                break;
            case 3:
                result = '3';
                break;
            case 4:
                result = '4';
                break;
            case 5:
                result = '5';
                break;
            case 6:
                result = '6';
                break;
            case 7:
                result = '7';
                break;
            case 8:
                result = '8';
                break;
            case 9:
                result = '9';
                break;
            case 10:
                result = 'A';
                break;
            case 11:
                result = 'B';
                break;
            case 12:
                result = 'C';
                break;
            case 13:
                result = 'D';
                break;
            case 14:
                result = 'E';
                break;
            case 15:
                result = 'F';
                break;
        }
        return result;
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        int counterNumbers = 1;
        long buff = a;
        while (buff / 16 != 0) {
            buff = buff / 16;
            counterNumbers++;
        }
        byte[] arrayOfNumbers = new byte[counterNumbers];
        for (byte i = 0; i < counterNumbers; i++) {
            byte remainder = (byte) (a % 16);
            a = a / 16;
            arrayOfNumbers[i] = remainder;
        }
        byte max = 16;
        byte resultIndex = 0;
        for (byte i = 0; i < counterNumbers; i++) {
            if (arrayOfNumbers[i] < max) {
                max = arrayOfNumbers[i];
                resultIndex = (byte) (i + 1);
            }
        }

        return resultIndex;
    }

}
