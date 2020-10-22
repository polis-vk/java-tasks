package ru.mail.polis.homework.collections.streams;

import java.text.CollationElementIterator;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SimpleStreams {

    /**
     * Реализуйте проверку на простоту входящего числа с помощью стримов.
     * Никаких циклов.
     * 1 балл
     */
    public static boolean isPrime(int n) {
        return n > 1 && IntStream.rangeClosed(2, n / 2).noneMatch(value -> n % value == 0);
    }

    /**
     * Вам передается текст и список плохих слов. Вам нужно вернуть мапу (слово -> количество упоминаний в тексте)
     * Слово - набор символ между началом строки/ концом строки / пробелами / знаками препинания (.,;:!?)
     * 1 балл
     */
    public static Map<String, Integer> createBadWordsDetectingStream(String text, List<String> badWords) {
        return Arrays
                .stream(text.split("[\n .,;:!?]"))
                .filter(badWords::contains)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, stringLongEntry -> stringLongEntry.getValue().intValue()));
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
    public static double calcDistance(double v, DoubleUnaryOperator changeV, double alpha, int n) {
        double[] speed = new double[]{ v };
        final double sin = Math.sin(alpha * 2);
        return IntStream.rangeClosed(1, n).mapToDouble(value -> {
            double buffer = speed[0];
            speed[0] = changeV.applyAsDouble(speed[0]);
            return buffer;
        }).reduce(0, (sum, value) -> sum + speed[0] * speed[0] * sin / 9.8);
    }
}
