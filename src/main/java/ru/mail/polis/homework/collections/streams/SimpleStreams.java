package ru.mail.polis.homework.collections.streams;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.DoubleStream;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;

public class SimpleStreams {

    /**
     * Реализуйте проверку на простоту входящего числа с помощью стримов.
     * Никаких циклов.
     * 1 балл
     */
    public static boolean isPrime(int n) {
        return IntStream.rangeClosed(2, (int)Math.sqrt(n))
        .noneMatch(i -> n % i == 0);
    }

    /**
     * Вам передается текст и список плохих слов. Вам нужно вернуть мапу (слово -> количество упоминаний в тексте)
     * Слово - набор символ между началом строки/ концом строки / пробелами / знаками препинания (.,;:!?)
     * 1 балл
     */
    public static Map<String, Integer> createBadWordsDetectingStream(String text, List<String> badWords) {
        return Arrays.stream(text.split("[\n .,;:!?]"))
                .filter(badWords::contains)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, wordMentions -> wordMentions.getValue().intValue()));
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
     */

    public static final double G = 9.8;
    
    public static double calcDistance(double v, DoubleUnaryOperator changeV, double alpha, int n) {
        final double sin = Math.sin(alpha * 2);
        return DoubleStream.iterate(v, changeV)
            .limit(n)
            .reduce(0, (sum, value) -> sum + value * value * sin / G);
    }
}
