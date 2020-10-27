package ru.mail.polis.homework.collections.streams;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Math.sin;

public class SimpleStreams {

    /**
     * Реализуйте проверку на простоту входящего числа с помощью стримов.
     * Никаких циклов.
     * 1 балл
     */
    public static boolean isPrime(int n) {
        if(n == 1){
            return false;
        }
        return IntStream.range(2, n-1)
                .allMatch(value -> n % value != 0);
    }

    /**
     * Вам передается текст и список плохих слов. Вам нужно вернуть мапу (слово -> количество упоминаний в тексте)
     * Слово - набор символ между началом строки/ концом строки / пробелами / знаками препинания (.,;:!?)
     * 1 балл
     */
    public static Map<String, Long> createBadWordsDetectingStream(String text, List<String> badWords) {
        String textWithoutSigns = text.replaceAll("[.,;:!?]", " ").toLowerCase();
        List<String> textWords = Arrays.asList(textWithoutSigns.split(" "));

        return badWords.stream()
                .collect(Collectors.toMap(value -> value, valueBadWords -> {
                    return textWords.stream()
                            .filter(value -> value.equals(valueBadWords))
                            .count();
                }));
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

    /*
        Я так понял что нужно посчитать сумму расстрояний его бросков?
        Не очень понятно что делает функция changeV, если она выдает скорость от броска, то зачем нам начальная скорость?
     */
    public static double calcDistance(double v, DoubleUnaryOperator changeV, double alpha, int n) {
        DoubleUnaryOperator distant = velocity -> velocity*velocity*Math.sin(2*alpha)/9.8;
        return IntStream.rangeClosed(1, n)
                .asDoubleStream()
                .map(changeV)
                .map(distant)
                .sum();
    }
}
