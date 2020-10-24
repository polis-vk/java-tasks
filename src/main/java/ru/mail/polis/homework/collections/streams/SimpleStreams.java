package ru.mail.polis.homework.collections.streams;

import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SimpleStreams {
    /**
     * Реализуйте проверку на простоту входящего числа с помощью стримов.
     * Никаких циклов.
     * 1 балл
     */
    public static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }

        return IntStream
                .range(2, n / 2)
                .noneMatch(x -> n % x == 0);
    }

    /**
     * Вам передается текст и список плохих слов. Вам нужно вернуть мапу (слово -> количество упоминаний в тексте)
     * Слово - набор символ между началом строки/ концом строки / пробелами / знаками препинания (.,;:!?)
     * 1 балл
     */
    public static Map<String, Integer> createBadWordsDetectingStream(String text, List<String> badWords) {
        return badWords.stream().collect(Collectors.toMap(x -> x, x -> text.split(x).length - 1));
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
        final double G = 9.8;
        final double SIN = Math.sin(alpha * 2);

        return IntStream.range(1, n)
                .mapToDouble(x -> x)
                .reduce(0,
                        (sum, h) -> sum + (Math.pow(changeV.applyAsDouble(h) * v, 2) * SIN / G));
    }
}
