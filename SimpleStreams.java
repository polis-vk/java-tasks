package ru.mail.polis.homework.collections.streams;

import java.util.*;
import java.util.function.DoubleUnaryOperator;
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
        return (n >= 2) && IntStream.rangeClosed(2,(int) Math.sqrt(n))
                .noneMatch(x -> n % x == 0);
    }

    /**
     * Вам передается текст и список плохих слов. Вам нужно вернуть мапу (слово -> количество упоминаний в тексте)
     * Слово - набор символ между началом строки/ концом строки / пробелами / знаками препинания (.,;:!?)
     * 1 балл
     */
    public static Map<String, Long> createBadWordsDetectingStream(String text, List<String> badWords) {
        return Arrays.stream(text.split("[.,;:!?\n ]"))
                .filter(badWords::contains)
                .collect(Collectors.groupingBy(Object::toString, Collectors.counting()));
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

    static final double G = 9.8;

    public static double calcDistance(double v, DoubleUnaryOperator changeV, double alpha, int n) {
        return DoubleStream.iterate(1, x -> x <= n, x -> x + 1)
                .limit(n)
                .map(number -> Math.pow(v + changeV.applyAsDouble(number), 2) * Math.sin(2 * alpha) / G)
                .sum();
    }
    
}
