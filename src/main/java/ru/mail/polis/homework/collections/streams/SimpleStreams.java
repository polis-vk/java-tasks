package ru.mail.polis.homework.collections.streams;

import java.util.*;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleStreams {

    /**
     * Реализуйте проверку на простоту входящего числа с помощью стримов.
     * Никаких циклов.
     * 1 балл
     */
    public static boolean isPrime(int n) {
        return IntStream.rangeClosed(2, (int) Math.ceil(Math.sqrt(n)))
                .takeWhile(divisor -> divisor <= Math.ceil(Math.sqrt(n)) && n % divisor != 0)
                .count() == (int) Math.ceil(Math.sqrt(n)) - 1;
    }

    /**
     * Вам передается текст и список плохих слов. Вам нужно вернуть мапу (слово -> количество упоминаний в тексте)
     * Слово - набор символ между началом строки/ концом строки / пробелами / знаками препинания (.,;:!?)
     * 1 балл
     */
    public static Map<String, Integer> createBadWordsDetectingStream(String text, List<String> badWords) {
        return Arrays.stream(text.split("[\\s.,;:!?]+"))
                .filter(badWords::contains)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.reducing(0, string -> 1, Integer::sum)));
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
    public static double calcDistance(double v, DoubleUnaryOperator changeV, double alpha, int n) {
        final double g = 9.8;

        return IntStream.range(0, n)
                .mapToObj(i -> IntStream.rangeClosed(0, i)
                        .mapToObj(j -> changeV)
                        .reduce(DoubleUnaryOperator.identity(), DoubleUnaryOperator::andThen)
                        .applyAsDouble(v))
                .mapToDouble(v0 -> v0 * v0 * Math.sin(2 * alpha) / g)
                .sum();
    }
}
