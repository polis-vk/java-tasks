package ru.mail.polis.homework.collections.streams;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.IntStream;

public class SimpleStreams {

    private final static double G = 9.8;
    /**
     * Реализуйте проверку на простоту входящего числа с помощью стримов.
     * Никаких циклов.
     * 1 балл
     */
    public static boolean isPrime(int n) {
        return false;
    }

    /**
     * Вам передается текст и список плохих слов. Вам нужно вернуть мапу (слово -> количество упоминаний в тексте)
     * Слово - набор символ между началом строки/ концом строки / пробелами / знаками препинания (.,;:!?)
     * 1 балл
     */
    public static Map<String, Integer> createBadWordsDetectingStream(String text, List<String> badWords) {
        return Collections.emptyMap();
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
        //Ne sovsem logichno, chto "changeV" ne zavisit ot nachalnoi skorosti "v",
        //poatomy v dannom slychae ona mozhet i nachalnuy znat (sorry for translit)
        return IntStream.rangeClosed(0, n - 1)
                .mapToDouble(changeV::applyAsDouble)
                .map(speed -> Math.pow(speed, 2) * Math.sin(Math.toRadians(2 * alpha)) / G)
                .sum();
    }
}
