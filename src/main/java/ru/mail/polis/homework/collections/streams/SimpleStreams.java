package ru.mail.polis.homework.collections.streams;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.DoubleStream;

public class SimpleStreams {

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
     * <p>
     * Посчитать расстояние на которое улетит мячик.
     * Для расчета дальности броска можно пользоваться физическими формулами движения и законом сохранения энергии.
     * g = 9.8
     * <p>
     * 3 балла
     */
    static final double g = 9.8;

    public static double calcDistance(double v, DoubleUnaryOperator changeV, double alpha, int n) {
        //Если я правильно понял задачу, то расстояния всех бросков нужно сложить...
        return DoubleStream.iterate(v, changeV)
                .limit(n)
                .map(el -> Math.pow(el, 2) * Math.sin(2 * alpha) / g)
                .reduce(Double::sum)
                .getAsDouble();
    }
}
