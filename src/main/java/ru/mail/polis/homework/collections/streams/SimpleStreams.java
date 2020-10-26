package ru.mail.polis.homework.collections.streams;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SimpleStreams {
    public static void main(String[] args) {
        System.out.println(createBadWordsDetectingStream("hello my name is Vadim, fg! hhg s hello!&", List.of("hello", "SIK")));
    }

    /**
     * Реализуйте проверку на простоту входящего числа с помощью стримов.
     * Никаких циклов.
     * 1 балл
     */
    public static boolean isPrime(int n) {
        return Stream.iterate(2, x -> x < Math.sqrt(n), x -> x += 1)
                .noneMatch(x -> n % x == 0);
    }

    /**
     * Вам передается текст и список плохих слов. Вам нужно вернуть мапу (слово -> количество упоминаний в тексте)
     * Слово - набор символ между началом строки/ концом строки / пробелами / знаками препинания (.,;:!?)
     * 1 балл
     */
    public static Map<String, Integer> createBadWordsDetectingStream(String text, List<String> badWords) {
        return Arrays.stream(text.split("[^aA-zZ]+"))
                .filter(badWords::contains)
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, x -> x.getValue().intValue()));
    }


    /**
     * Маленький мальчик кидает мячик n раз в поле силы тяжести под углом alpha к поверхности земли.
     * Так же известно, что мальчик устает с каждым броском все больше. Дана начальная скорость v - скорость
     * при первом броске и функция изменения скорости от номера броска - changeV
     *
     * Посчитать расстояние на которое улетит мячик.
     * Для расчета дальности броска можно пользоваться физическими формулами движения и законом сохранения энергии.
     * g = 9.8
     *
     * 3 балла
     *
     * Решение:
     *
     * |h       ___
     * |      /  | \
     * |    /    |   \
     * |  /t(пол)|     \
     * |_________|_________
     *          l
     *  Надо найти: l = cos (a) * V * t
     *
     *  Условие что на вершине он как бы замрет:
     *  V * sin (a) - g * t = 0  ->
     *      t половины = V * sin (a) / g ->
     *          t = 2 * V * sin (a) / g
     *
     *  -> l = cos (a) * V * (2 * V * sin (a) / g) ->
     *      l = 2 * cos (a) * sin (a) * V^2 / g)
     *
     *  ( 2 * cos (a) * sin (a) == sin (2 * a) )
     *
     *  Ответ: l = sin (2 * a) * V^2 / g
     *
     *
     */
    public static double calcDistance(double v, DoubleUnaryOperator changeV, double alpha, int n) {
        final double g = 9.8;
        final double sinTwoAlpha = Math.sin(2 * alpha);
        return IntStream.iterate(1, x-> x <= n, x -> x += 1)
                .mapToDouble(x -> x)
                .reduce(0, (left, operand) ->
                        Math.pow(changeV.applyAsDouble(operand) * v, 2))
                * sinTwoAlpha / g;
    }
}
