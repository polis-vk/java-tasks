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
    return (q == 1) ? (long) a * n : (long) (a * (Math.pow(q, n) - 1) / (q - 1));
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
    int stepY = (up - down);
    int stepX = (right - left);
    int res = (up >= grassY) ? 1 : (right >= grassX ? 1 : Integer.MAX_VALUE);
    if (res == 1) {
      return res;
    }
    if (stepX * grassX > 0) {
      res = Math.min(res, ((grassX - right) + stepX - 1) / stepX + 1);
    }
    if (stepY * grassY > 0) {
      res = Math.min(res, ((grassY - up) + stepY - 1) / stepY + 1);
    }
    return res;
  }

  /**
   * Дано число n в 10-ном формате и номер разряда order.
   * Выведите цифру стоящую на нужном разряде для числа n в 16-ом формате
   * Нельзя пользоваться String-ами
   * Пример: (454355, 2) -> D
   */
  private static final int radix = 16;

  public static char kDecimal(int n, int order) {
    int numberPosition = n / ((int) Math.pow(radix, order - 1));
    int answer = numberPosition % radix;
    return (answer < 10) ? (char) (answer + 48) : (char) (answer + 55);
  }

  /**
   * Дано число в 10-ном формате.
   * Нужно вывести номер минимальной цифры для числа в 16-ном формате. Счет начинается справа налево,
   * выводим номер первой минимальной цифры (если их несколько)
   * Нельзя пользоваться String-ами
   * (6726455) -> 2
   */
  public static byte minNumber(long a) {
    int minNumber = (int) (a % radix);
    long b = a / radix;
    byte minIndex = 1, step = 2;
    while (b >  0) {
      int tmp = (int) (b % radix);
      if (tmp < minNumber) {
        minNumber = tmp;
        minIndex = step;
      }
      step++;
      b /= radix;
    }
    return minIndex;
  }

}
