package ru.mail.polis.homework.collections.streams;

import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SimpleStreams {

  /**
   * Реализуйте проверку на простоту входящего числа с помощью стримов.
   * Никаких циклов.
   * 1 балл
   */
  public static boolean isPrime(int n) {
    return (n > 1) &&
            IntStream.range(2, n)
                    .noneMatch(x -> n % x == 0);
  }

  /**
   * Вам передается текст и список плохих слов. Вам нужно вернуть мапу (слово -> количество упоминаний в тексте)
   * Слово - набор символ между началом строки/ концом строки / пробелами / знаками препинания (.,;:!?)
   * 1 балл
   */
  public static Map<String, Integer> createBadWordsDetectingStream(String text, List<String> badWords) {
    return Stream.of(text.split("[\\n\\s.,;:!?]+"))
            .filter(badWords::contains)
            .collect(Collectors.toMap(Function.identity(), val -> 1, Integer::sum));
  }


  /**
   * Маленький мальчик кидает мячик n раз в поле силы тяжести под углом alpha к поверхности земли.
   * Так же известно, что мальчик устает с каждым броском все больше. Дана начальная скорость v - скорость
   * при первом броске и функция изменения скорости от номера броска - changeV
   * <p>
   * Посчитать расстояние на которое улетит мячик.
   * Для расчета дальности броска можно пользоваться физическими формулами движения и законом сохранения энергии.
   * g = 9.8
   * <p>
   * 3 балла
   */

  static final double G = 9.8;

  public static double calcDistance(double v, DoubleUnaryOperator changeV, double alpha, int n) {

    final double sinTwoAlpha = Math.sin(2 * alpha);

    return DoubleStream.iterate(v, changeV)
            .limit(n)
            .reduce(0, (sum, element) -> sum + Math.pow(element, 2) * sinTwoAlpha / G);
  }
}
