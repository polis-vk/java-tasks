package ru.mail.polis.homework.simple;

/**
 * Возможно вам понадобится класс Math с его методами. Например, чтобы вычислить квадратный корень, достаточно написать
 * Math.sqrt(1.44)
 * Чтобы увидеть все методы класса Math, достаточно написать Math. и среда вам сама покажет возможные методы.
 * Для просмотра подробной документации по выбранному методу нажмите Ctrl + q
 */
public class DoubleAdvancedTask {

  /**
   * Вывести три корня кубического уравнения через запятую: a * x ^ 3 + b * x ^ 2 + c * x + d = 0;
   * Вывод менять не нужно, надо только посчитать x1, x2 и x3, где x1 >= x2 >= x3
   * Считаем, что все три корня вещественные.
   * <p>
   * Если используете какой-то конкретный способ, напишите какой.
   * Пример: (1, -4, -7, 10) -> "-2.0, 1.0, 5.0"
   */
  public static String equation(int a, int b, int c, int d) {
    double x1 = 0;
    double x2 = 0;
    double x3 = 0;
    double b1 = (double) b / a;
    double b2 = (double) c / a;
    double b3 = (double) d / a;

    return x1 + ", " + x2 + ", " + x3;
  }

  /**
   * Нужно посчитать расстояние, между двумя прямыми
   * Примеры: (1, 1, 2, -1) -> 0
   * (0, 1, 0, 5) -> 4
   */
  public static float length(double a1, double b1, double a2, double b2) {
    if (a1 != a2) {
      return 0;
    }
    return (float) (Math.abs(b1 - b2) / Math.sqrt(a1 * a2 + 1));
  }

  /**
   * Даны три точки в пространстве (x1, y1, z1) , (x2, y2, z2) и (x3, y3, z3). Вам нужно найти Z координату
   * четвертой точки (x4, y4, z4), которая находится на той же плоскости что и первые три.
   * (0, 0, 1,
   * 1, 1, 1,
   * 10, 100, 1,
   * 235, -5) -> 1
   */

  private static class Vector {
    private int x;
    private int y;
    private int z;

    public Vector(int x, int y, int z) {
      this.x = x;
      this.y = y;
      this.z = z;
    }
  }

  public static class VectorOperations {
    public static Vector multiplication(Vector a, Vector b) {
      return new Vector(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
    }
  }

  public static double surfaceFunction(int x1, int y1, int z1,
                                       int x2, int y2, int z2,
                                       int x3, int y3, int z3,
                                       int x4, int y4) {
    Vector m12 = new Vector(x2 - x1, y2 - y1, z2 - z1);
    Vector m13 = new Vector(x3 - x1, y3 - y1, z3 - z1);
    Vector n = VectorOperations.multiplication(m12, m13);
    return (double) (-n.x * (x4 - x1) - n.y * (y4 - y1)) / n.z + z1;
  }
}
