package ru.mail.polis.homework.collections.streams;

import java.util.*;
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
        if (n <= 1) {
            return false;
        }
        return IntStream.rangeClosed(2, n / 2)
                .noneMatch(i -> n % i == 0);
    }

    /**
     * Вам передается текст и список плохих слов. Вам нужно вернуть мапу (слово -> количество упоминаний в тексте)
     * Слово - набор символ между началом строки/ концом строки / пробелами / знаками препинания (.,;:!?)
     * 1 балл
     *
     * @return
     */
    public static Map<String, Integer> createBadWordsDetectingStream(String text, List<String> badWords) {
        return Arrays.stream(text
                .split("[.,;:!?\\s]+"))
                .filter(badWords::contains)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.reducing(0, n -> 1, Integer::sum)));
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
    private static final double G = 9.8;

    public static double calcDistance(double v, DoubleUnaryOperator changeV, double alpha, int n) {
        final double sin = Math.sin(2 * alpha) / G;
        return DoubleStream.iterate(v, changeV)
                .limit(n)
                .reduce((s, vNext) -> s + Math.pow(vNext, 2) * sin).orElse(0);
    }

    public static void main(String[] args) {
        //prime numbers
        long count = Stream.iterate(0, n -> n + 1)
                .limit(10)
                .filter(SimpleStreams::isPrime)
                .peek(System.out::println)
                .count();
        System.out.println("Total prime numbers: " + count);

        //map
        List<String> badWords = new LinkedList<>();
        badWords.add("a");
        badWords.add("b");
        badWords.add("c");

        String text = "a;a;b b,c\nc?a\ta v";

        Map<String, Integer> map = createBadWordsDetectingStream(text, badWords);
        for (String name : map.keySet()) {
            String value = map.get(name).toString();
            System.out.println(name + " " + value);
        }

        //distance
        double alpha = Math.PI / 4;
        double v = 100;
        int n = 6;
        System.out.println(calcDistance(v, op -> v / n, alpha, n));
    }
}
