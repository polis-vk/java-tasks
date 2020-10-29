package ru.mail.polis.homework.collections.streams;

import java.util.*;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class SimpleStreams {

    private final static double G = 9.8;

    /**
     * Реализуйте проверку на простоту входящего числа с помощью стримов.
     * Никаких циклов.
     * 1 балл
     */
    public static boolean isPrime(int n) {
        return n == 1 || IntStream.rangeClosed(2, (int)Math.sqrt(n)).noneMatch(x -> n % x == 0);
    }

    /**
     * Вам передается текст и список плохих слов. Вам нужно вернуть мапу (слово -> количество упоминаний в тексте)
     * Слово - набор символ между началом строки/ концом строки / пробелами / знаками препинания (.,;:!?)
     * 1 балл
     */
    public static Map<String, Integer> createBadWordsDetectingStream(String text, List<String> badWords) {
        return Arrays.stream(text.split("[.,;:!?\\s\\n]+"))
                .filter(badWords::contains)
                .collect(Collectors.groupingBy(Object::toString, Collectors.summingInt(i -> 1)));
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
        final double sin = Math.sin(2 * alpha);
        return DoubleStream.iterate(v, changeV)
                .limit(n)
                .reduce(0.0, (total, v0) -> total + Math.pow(v0, 2.0) * sin / G);
    }
}
