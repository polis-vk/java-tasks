package ru.mail.polis.homework.collections.streams;

import java.util.*;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
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
        return IntStream.rangeClosed(2, (int)Math.sqrt(n)).noneMatch(i -> n % i == 0);
    }


    /**
     * Вам передается текст и список плохих слов. Вам нужно вернуть мапу (слово -> количество упоминаний в тексте)
     * Слово - набор символ между началом строки/ концом строки / пробелами / знаками препинания (.,;:!?)
     * 1 балл
     */
//    public static Map<String, Integer> createBadWordsDetectingStream(String text, List<String> badWords) {
    public static Map<String, Long> createBadWordsDetectingStream(String text, List<String> badWords) {

        Map<String, Long> map = new HashMap<>();
        for (String word : badWords) {
            Stream<String> stream = Stream.of(text.split("[\\s.,;:!?]+"));
            Long count = stream.map(String::toLowerCase)
                    .filter(word::equals).count();
            map.put(word, count);
        }
        return map;
        //вероятно стоит найти более красивое решение
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
        return 0;
    }


    public static void main(String[] args) {
        System.out.println(isPrime(5));
        System.out.println(isPrime(0));
        System.out.println(isPrime(1));
        System.out.println(isPrime(2));
        System.out.println(isPrime(6));

        List<String> badWords = new ArrayList<>();
        badWords.add("типа");
        badWords.add("ну");
        badWords.add("блин");
        System.out.println(createBadWordsDetectingStream("блин, ну как бы типа много слов блин паразитов ну", badWords));
    }
}
