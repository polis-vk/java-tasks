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
        long sum = 0;
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
        int time = 0;
        int x = 0, y = 0;
        double distance_x = grassX;//дистанция до оси х на конец n дня
        double distance_y = grassY;//дистанция до оси y на конец n дня
        while (true) {
            time += 1;
            for (int i = 0; i < up; i++) {
                if (grassX == x || grassY == y) {
                    return time;
                }
                y += 1;
            }
            for (int i = 0; i < right; i++) {
                if (grassX == x || grassY == y) {
                    return time;
                }
                x += 1;
            }
            for (int i = 0; i < down; i++) {
                if (grassX == x || grassY == y) {
                    return time;
                }
                y -= 1;
            }
            for (int i = 0; i < left; i++) {
                if (grassX == x || grassY == y) {
                    return time;
                }
                x -= 1;
            }
            double new_distance_x = Math.abs(grassX - x);//текущая дистанция х
            double new_distance_y = Math.abs(grassY - y);//текущая дистанция у
            //сравниваем предыдущую и текущую дистанцию. Если текущая дистанция по х и по у увеличивается, значит,
            //мы никогда не достигнем травы
            if (distance_x > new_distance_x || distance_y > new_distance_y) {
                distance_x = new_distance_x;
                distance_y = new_distance_y;
            } else {
                return Integer.MAX_VALUE;
            }
        }
    }

    /**
     * Дано число n в 10-ном формате и номер разряда order.
     * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
     * Нельзя пользоваться String-ами
     * Пример: (454355, 2) -> D
     */
    public static char kDecimal(int n, int order) {
        String s = "";
        int temp;
        while (n > 16) {
            temp = n % 16;
            s += Letter(temp);
            n /= 16;
        }
        s += Letter(n);
        return s.charAt(order - 1);
    }

    public static String Letter(int temp) {
        if (temp > 9) {
            switch (temp) {
                case 10:
                    return "A";
                case 11:
                    return "B";
                case 12:
                    return "C";
                case 13:
                    return "D";
                case 14:
                    return "E";
                case 15:
                    return "F";
            }
        } else {
            String s = "";
            s += temp;
            return s;
        }
        return " ";
    }

    /**
     * Дано число в 10-ном формате.
     * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
     * выводим номер первой минимальной цифры (если их несколько)
     * Нельзя пользоваться String-ами
     * (6726455) -> 2
     */
    public static byte minNumber(long a) {
        String s = "";
        int temp;
        long min = 16;
        byte count = 0;
        byte num = 0;
        while (a > 16) {
            count++;
            if (a % 16 < min) {
                min = a % 16;
                num = count;
            }
            a /= 16;
        }
        if (a < min) {
            min = a;
            num = ++count;
        }
        return num;
    }

}
