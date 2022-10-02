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
        int sum = 0;
        for (int i = 0; i < n; i++) {
            sum += a * Math.pow(q, i);
        }
        return sum;
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
        if ((grassX <= 0) || (grassY <=0)) {
            return 0;
        }

        int currentX = 0;
        int currentY = 0;
        int counter = 1;

        while ((currentX < grassX) && (currentY < grassY)) {
            currentX += right;
            currentY += up;

            if ((currentX >= grassX) || (currentY >= grassY)) {
                return counter;
            }

            currentX -= left;
            currentY -= down;

            if ((currentX <= 0) && (currentY <= 0)) {
                return Integer.MAX_VALUE;
            }

            counter++;
        }
        return 0;
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        int remainder = 0;

        for (int i = 0; i < order; i++) {
            remainder = n % 16;
            n = n / 16;
        }

        if (remainder > 9) {
            switch (remainder) {
                case 10:
                    return 'A';
                case 11:
                    return 'B';
                case 12:
                    return 'C';
                case 13:
                    return 'D';
                case 14:
                    return 'E';
                case 15:
                    return 'F';
            }
        }
        else {
            return (char) (remainder + '0');
        }
        return 0;
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        int amountOfNumbers = 1;
        long remainder;

        while (a >= Math.pow(16, amountOfNumbers) - 1) {
            if (a == Long.MAX_VALUE) {
                amountOfNumbers = 16;
                break;
            }
            amountOfNumbers++;
        }

        char[] arrayOfNumbers = new char[amountOfNumbers];
        for (int i = 0; i < amountOfNumbers; i++) {
            remainder = a % 16;
            if (remainder > 9) {
                switch ((int) remainder) {
                    case 10:
                        arrayOfNumbers[i] = 'A';
                        break;
                    case 11:
                        arrayOfNumbers[i] = 'B';
                        break;
                    case 12:
                        arrayOfNumbers[i] = 'C';
                        break;
                    case 13:
                        arrayOfNumbers[i] = 'D';
                        break;
                    case 14:
                        arrayOfNumbers[i] = 'E';
                        break;
                    case 15:
                        arrayOfNumbers[i] = 'F';
                }
            }
            else {
                arrayOfNumbers[i] = (char) (remainder + '0');
            }
            a = a / 16;
        }
        int min = 'F';
        byte minOrder = 0;
        for (byte i = 0; i < arrayOfNumbers.length; i++) {
            if (arrayOfNumbers[i] < min) {
                min = arrayOfNumbers[i];
                minOrder = i;
            }
        }
        return minOrder += 1;
    }

}
